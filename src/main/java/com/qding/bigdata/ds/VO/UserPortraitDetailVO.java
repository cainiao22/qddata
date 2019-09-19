package com.qding.bigdata.ds.VO;

public class UserPortraitDetailVO {
    public String m_id;
    public String p_id;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public UserPortraitDetailVO(String m_id, String p_id) {
        this.m_id = m_id;
        this.p_id = p_id;
    }
}
