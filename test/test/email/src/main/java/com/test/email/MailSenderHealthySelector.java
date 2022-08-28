package com.test.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author luyi
 * 监控选择器
 */
public class MailSenderHealthySelector implements MailSenderSelector {

    private final Map<String, List<MailSender>> groupSelectMap = new ConcurrentHashMap<>(8, 1);

    @Override
    public MailSender selectOne(String group) {
        return null;
    }

    @Override
    public MailSender selectHealthyOne(String group) {
        List<MailSender> mailSenders = groupSelectMap.get(group);
        List<MailSender> healthyMailSenders = mailSenders.stream().filter(e -> e.getHealthyStatus().equals(HealthStatus.healthy.getStatus())).collect(Collectors.toList());
        int size = healthyMailSenders.size();
        //随机
        return healthyMailSenders.get((int) (Math.random() * size));
    }

    @Override
    public void register(MailSender mailSender) {
        String group = mailSender.getGroup();
        List<MailSender> mailSends = groupSelectMap.get(mailSender.getGroup());
        if (mailSends == null) {
            synchronized (this) {
                if (!groupSelectMap.containsKey(mailSender.getGroup())) {
                    mailSends = new ArrayList<>(5);
                    groupSelectMap.put(group, mailSends);
                } else {
                    mailSends = groupSelectMap.get(mailSender.getGroup());
                }
            }
        }
        mailSends.add(mailSender);
    }
}
