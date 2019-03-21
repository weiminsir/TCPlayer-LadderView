package com.wick.threadlib.download;

import static com.wick.threadlib.download.DownLoadManager.STATE_DELETE;
import static com.wick.threadlib.download.DownLoadManager.STATE_PAUSED;

/**
 * @Description:
 * @Author:weiminzhang
 * @Created:2019/3/15
 */
public class DownLoadTask implements Runnable {

    private DownLoadBean bean;

    public DownLoadTask(DownLoadBean bean) {
        this.bean = bean;
    }

    @Override
    public void run() {
        // 等待中就暂停了
//        if (bean.downloadState == STATE_PAUSED) {
//            bean.downloadState = STATE_PAUSED;
//            DataBaseUtil.UpdateDownLoadById(bean);
//            return;
//        } else if (bean.downloadState == STATE_DELETE) {// 等待中就删除直接回调界面，然后直接返回
//            bean.downloadState = STATE_DELETE;
//            notifyDownloadStateChanged(bean);
//            mTaskMap.remove(bean.id);
//            return;
//        }
//
//        bean.downloadState = DownLoadManager.STATE_START;// 开始下载
//        DataBaseUtil.UpdateDownLoadById(bean);
//        notifyDownloadStateChanged(bean);
//
//        // 当前下载的进度
//        long compeleteSize = 0;
//        File file = new File(bean.getPath());// 获取下载文件
//        if (!file.exists()) {
//            // 如果文件不存在
//            bean.currentSize = 0;
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // 如果存在就拿当前文件的长度，设置当前下载长度
//            // (这样的好处就是不用每次在下载文件的时候都需要写入数据库才能记录当前下载的长度，一直操作数据库是很费资源的)
//            compeleteSize = file.length();
//        }
//        try {
//            URL url = new URL(bean.url);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setConnectTimeout(30 * 1000);
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Range", "bytes=" + compeleteSize
//                    + "-" + bean.appSize);
//
//            // 获取的状态码
//            int code = connection.getResponseCode();
//            // 判断是否能够断点下载
//            if (code == 206) {
//                @SuppressWarnings("resource")
//                OutputStream outputStream = new FileOutputStream(file, true);
//                // 将要下载的文件写到保存在保存路径下的文件中
//                InputStream is = connection.getInputStream();
//                byte[] buffer = new byte[102400];
//                int length = -1;
//                // 进入下载中状态
//                bean.downloadState = STATE_DOWNLOADING;
//                DataBaseUtil.UpdateDownLoadById(bean);
//
//                while ((length = is.read(buffer)) != -1) {
//                    // 暂停就回调，然后直接返回
//                    if (bean.downloadState == STATE_PAUSED) {
//                        bean.downloadState = STATE_PAUSED;
////                        DataBaseUtil.UpdateddateDownLoadById(bean);
////                        notifyDownloadStateChanged(bean);
//                        return;
//                    } else if (bean.downloadState == STATE_DELETE) {// 下载的时候删除直接回调界面，然后直接返回
//                        bean.downloadState = STATE_DELETE;
//                        notifyDownloadStateChanged(bean);
//                        mTaskMap.remove(bean.id);
//                        return;
//                    }
//                    // 把当前下载的bean给全局记录的bean
//                    down_bean = bean;
//                    outputStream.write(buffer, 0, length);
//                    compeleteSize += length;
//                    // 更新数据库中的下载信息
//                    // 用消息将下载信息传给进度条，对进度条进行更新
//                    bean.currentSize = compeleteSize;
//                    notifyDownloadStateChanged(bean);
//                }
//                if (bean.appSize == bean.currentSize) {
//                    bean.downloadState = STATE_DOWNLOADED;
//                    DataBaseUtil.UpdateDownLoadById(bean);
//                    notifyDownloadStateChanged(bean);
//                } else {
//                    bean.downloadState = STATE_ERROR;
//                    DataBaseUtil.UpdateDownLoadById(bean);
//                    notifyDownloadStateChanged(bean);
//                    bean.currentSize = 0;// 错误状态需要删除文件
//                    file.delete();
//                }
//            } else {
//                Log.e("123456", "不支持断点下载");
//            }
//        } catch (IOException e) {
//            bean.downloadState = STATE_ERROR;
//            DataBaseUtil.UpdateDownLoadById(bean);
//            notifyDownloadStateChanged(bean);
//            bean.currentSize = 0;// 错误状态需要删除文件
//            file.delete();
//        }
//        mTaskMap.remove(bean.id);
    }
}
