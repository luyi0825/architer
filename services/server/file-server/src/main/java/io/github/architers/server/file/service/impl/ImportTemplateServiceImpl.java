package io.github.architers.server.file.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.context.model.TreeNode;
import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.objectstorage.ObjectStorage;
import io.github.architers.objectstorage.ObjectStorageType;
import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.server.file.domain.entity.CheckInfo;
import io.github.architers.server.file.domain.param.FileTemplateAddParams;
import io.github.architers.server.file.eums.FileContentType;
import io.github.architers.server.file.utils.FileVersionUtils;
import io.github.architers.server.file.mapper.ImportTemplateCatalogDao;
import io.github.architers.server.file.mapper.ImportTemplateDao;
import io.github.architers.server.file.domain.dto.TemplateCatalogDTO;
import io.github.architers.server.file.domain.dto.TemplateDTO;
import io.github.architers.server.file.domain.entity.Template;
import io.github.architers.server.file.domain.entity.TemplateCatalog;
import io.github.architers.server.file.eums.FileType;
import io.github.architers.server.file.service.ImportTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * 导入模板对应的service实现类
 *
 * @author luyi
 */
@Service
@Slf4j
public class ImportTemplateServiceImpl implements ImportTemplateService {

    @Resource
    private ImportTemplateCatalogDao importTemplateCatalogDao;

    @Resource
    private ImportTemplateDao importTemplateDao;

    @Resource(name = ObjectStorageType.OSS)
    private ObjectStorage objectStorage;

    @Override
    public void addTemplateCatalog(TemplateCatalogDTO templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        importTemplateCatalogDao.insert(importTemplateCatalog);
    }

    @Override
    public void editTemplateCatalog(TemplateCatalogDTO templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        importTemplateCatalogDao.updateById(importTemplateCatalog);
    }

    @Override
    public List<TreeNode> getTemplateCatalog() {
        List<TemplateCatalog> templateCatalogs = importTemplateCatalogDao.selectList(null);
        return NodeTreeUtils.convertToTree(templateCatalogs, "parentId", new Function<TemplateCatalog, TreeNode>() {
            @Override
            public TreeNode apply(TemplateCatalog templateCatalog) {
                TreeNode treeNode = new TreeNode();
                treeNode.setParentCode(templateCatalog.getParentId() + "");
                treeNode.setCaption(templateCatalog.getCatalogCaption());
                treeNode.setCode(templateCatalog.getId() + "");
                return treeNode;
            }
        });
    }

    @Override
    public void addTemplate(TemplateDTO templateDTO) {
        TemplateCatalog templateCatalog = importTemplateCatalogDao.selectById(templateDTO.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        Template template = new Template();
        BeanUtils.copyProperties(templateDTO, template);
        templateCatalog.setCreateTime(new Date());
        importTemplateDao.insert(template);
    }

    @Override
    public void editTemplate(TemplateDTO templateDTO) {
        Template template = new Template();
        BeanUtils.copyProperties(templateDTO, template);
        importTemplateDao.updateById(template);
    }


    /**
     * 支持模板
     */
    private static final List<FileType> SUPPORT_VERSION_TYPES = new ArrayList<>();

    static {
        SUPPORT_VERSION_TYPES.add(FileType.xlsx);
    }

    /**
     * @param file
     * @param templateId
     * @param refreshVersion
     * @throws IOException
     */
    @Override
    public void uploadTemplateFile(MultipartFile file, Integer templateId, boolean refreshVersion) throws IOException {
        Template template = importTemplateDao.selectById(templateId);
        String key = "/importTemplate";
        String contentType = file.getContentType();
        FileType fileType = FileType.convertToContentTypeMap().get(contentType);
        if (template.getSavePath().startsWith("/")) {
            key =
                    key + template.getSavePath();
        } else {
            key = key + "/" + template.getSavePath();
        }
        if (!template.getSavePath().endsWith("/")) {
            key = key + "/";
        }
        key = key + template.getTemplateCode() + fileType.getSuffix();

        PutFileResponse putFileResponse;
        File tempFile = null;
        try {
            if (refreshVersion) {


                if (!SUPPORT_VERSION_TYPES.contains(fileType)) {
                    throw new BusLogException("该文件类型暂不支持刷新版本");
                }
                String newVersion = UUID.randomUUID().toString();
                template.setVersion(newVersion);
                tempFile = FileVersionUtils.fillFileVersion(file.getInputStream(), newVersion);
                putFileResponse = objectStorage.putObject(tempFile, key);
            } else {
                putFileResponse = objectStorage.putObject(file.getInputStream(), key);
            }
            if (!putFileResponse.isResult()) {
                throw new BusLogException("上传文件失败:" + putFileResponse.getErrorMessage());
            }
            template.setTemplateUrl(putFileResponse.getUrl());
            importTemplateDao.updateById(template);
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }


    }

    @Override
    public String getNewTemplateFileVersion(String templateCode) {
        Wrapper<Template> templateWrapper = Wrappers.lambdaQuery(Template.class)
                .select(Template::getTemplateCode, Template::getVersion)
                .eq(Template::getTemplateCode, templateCode);
        Template template = importTemplateDao.selectOne(templateWrapper);
        if (template == null) {
            throw new BusLogException("模板文件不存在");
        }
        return template.getVersion();
    }

    @Override
    public void addTemplateFile(FileTemplateAddParams fileTemplateAddParams, MultipartFile file) throws IOException {
        File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
        Tika tika = new Tika();
        String type = tika.detect(tempFile);
        if (!FileContentType.xlsx.getContentType().equals(type)) {
            throw new BusException("该模板文件暂时不支持");
        }
        CheckInfo checkInfo = fileTemplateAddParams.getCheckInfo();
        CheckInfo.FileVersion fileVersion = checkInfo.getFileVersion();
        if (Boolean.TRUE.equals(fileVersion.getEnableCheck())) {
            //TODO 填充版本
        }
        CheckInfo.RowInfo rowInfo = checkInfo.getRowInfo();
        if (Boolean.TRUE.equals(rowInfo.getEnableCheck())) {
            Assert.notNull(rowInfo.getStartRow(), "开始行不能为空");
            Assert.notNull(rowInfo.getEndRow(), "结束行不能为空");
            if (rowInfo.getEndRow() < rowInfo.getStartRow()) {
                throw new BusException("结束行不能小于开始行");
            }
        }

        CheckHeadReadListener checkHeadReadListener = new CheckHeadReadListener(rowInfo.getStartRow(), rowInfo.getEndRow());

        EasyExcel.read(tempFile, checkHeadReadListener).doReadAll();
        System.out.println(checkHeadReadListener.headDataList);

        System.out.println(type);
    }

    class CheckHeadReadListener implements ReadListener<LinkedHashMap<Integer, String>> {

        private final int startRow;

        private final int endRow;

        private final List<LinkedHashMap<Integer, String>> headDataList;


        public CheckHeadReadListener(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
            headDataList = new ArrayList<>(endRow - startRow + 1);
        }

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            log.error("读取头失败");
            throw exception;
        }

        @Override
        public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
            addCheckHeads(context);
            System.out.println(headMap);
        }

        @Override
        public void invoke(LinkedHashMap data, AnalysisContext context) {
            if (context.readRowHolder().getRowIndex() > 100) {
                throw new BusLogException("没有读取到头数据");
            }
            addCheckHeads(context);
        }

        private void addCheckHeads(AnalysisContext context) {
            ReadRowHolder readRowHolder = context.readRowHolder();
            int rowNumber = context.readRowHolder().getRowIndex() + 1;
            if (startRow <= rowNumber && endRow >= rowNumber) {
                Map<Integer, Cell> readCellDataLinkedHashMap = readRowHolder.getCellMap();
                LinkedHashMap<Integer, String> headDataMap = new LinkedHashMap<>();
                readCellDataLinkedHashMap.forEach((key, cellData) -> {
                    ReadCellData<?> readCellData = (ReadCellData<?>) cellData;
                    String stringValue = null;
                    if (readCellData != null) {
                        stringValue = readCellData.getStringValue();
                    }
                    headDataMap.put(key, stringValue);
                });
                headDataList.add(headDataMap);
                System.out.println(rowNumber);
            }
        }


        @Override
        public void extra(CellExtra extra, AnalysisContext context) {
            ReadListener.super.extra(extra, context);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {

        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            int rowNumber = context.readRowHolder().getRowIndex() + 1;
            if (rowNumber <= endRow) {
                return true;
            }
            return false;
        }
    }
}
