基于springboot和springcloud的一个社交平台 只完成后端部分，后面有空再搭前端//
已完成的点：//
1.公共类 规范输出，分页，以及返回状态码//
2.文章微服务：可以发布文章，修改文章，删除文章，查询所有文章，查询单个文章，针对某个文章属性进行搜索，对文章进行审核，点赞//
3.评论微服务：因为评论的数据量较大，用nosql mongodb进行储存，完成对评论的增删改查，以及查询出指定文章的评论，对评论的点赞为mongodb自带的mongodbtemplate完成//
