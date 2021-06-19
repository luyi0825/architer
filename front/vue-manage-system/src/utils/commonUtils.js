/**
 * @author luyi
 * 公共工具
 */
export default {
  /**
   * 展示响应消息，并增加回调
   * @param obj 调用的对象
   * @param data 数据
   * @param successFunction 成功后调用的函数
   * @param errorFunction 失败调用的函数
   */
  showResponseMessage (obj, data, successFunction, errorFunction) {
    if (data.code === 200) {
      obj.$message({
        type: 'success',
        message: '操作成功'
      })
      if (successFunction) {
        successFunction()
      }
    } else {
      obj.$message.error(data.msg)
      if (errorFunction) {
        errorFunction()
      }
    }
  },

  /**
   * 展示响应错误的消息
   * @param obj
   * @param data
   * @param successFunction 成功后调用的函数
   * @param errorFunction 失败调用的函数
   */
  showResponseErrorMessage (obj, data, successFunction, errorFunction) {
    if (data.code !== 200) {
      obj.$message({
        type: 'error',
        message: data.message || '操作失败'
      })
      if (errorFunction) {
        errorFunction()
      }
      return
    }
    // 回调函数
    if (successFunction) {
      successFunction()
    }
  },
  /**
   *  展示错误的消息
   * @param obj
   * @param errorMessage
   */
  showErrorMessage (obj, errorMessage) {
    obj.$message({
      type: 'error',
      message: errorMessage || '操作失败'
    })
  }
}
