package com.limemartini.constants;

public class SystemConstants
{

    /**
     *  status: the article is still a draft
     */
    public static final String ARTICLE_STATUS_DRAFT = "1";
    /**
     *  status: the article is published normally
     */
    public static final String ARTICLE_STATUS_NORMAL = "0";

    public static final String CATEGORY_STATUS_NORMAL = "0";

    public static final String LINK_STATUS_NORMAL = "0";

    // comment type: comment of article
    public static final String ARTICLE_COMMENT = "0";

    // comment type: comment of link
    public static final String LINK_COMMENT = "1";

    public static final String REDIS_VIEW_COUNT = "article:viewCount";
    public static final String MENU = "C";
    public static final String BUTTON = "F";
    public static final String STATUS_NORMAL = "0";
    public static final String ADMIN = "1";
    public static final Long SUPER_ADMIN_ID = 0L;
}