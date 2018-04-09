# Calendar_Schedule

1. 日历界面，选中日期则为蓝色背景，点击绿色按钮跳转至添加日程界面；菜单栏可以选择查看日历或者直接查看日程。

2. 添加日程界面，在这里可以编辑你的日程，下拉菜单选择日期和时间(默认的日期为在日历界面选中的日期，默认时间为系统当前时间)；闹钟提醒复选框待实现；点击浏览图片获取手机权限打开相册即可选择图片(由于后期存入数据库时采用二进制字节流，太大可能会造成程序崩溃)；点击添加日程即添加当前相关日程信息到数据库中，程序跳转到日程查看界面；取消则返回主(日历)界面。

3. 日程查看界面，日程通过ListView控件显示部分简要信息，右上角菜单栏可以选择进入日历/日程界面；单击某一日程进入编辑日程界面。

4.  编辑日程界面，与添加日程界面相似可以修改日程相关信息，黄色按钮确认修改选中日程；灰色按钮删除选中日程。