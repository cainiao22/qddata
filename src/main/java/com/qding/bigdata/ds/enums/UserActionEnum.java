package com.qding.bigdata.ds.enums;

/**
 * 用户行为类型枚举
 * @author QDHL
 *
 */
public enum UserActionEnum {
	
		SEL_GOODS_DETAILS("selGoodsDetails","interesting_buy"),//商品详情行为
		GET_TOPIC_DETAIL("getTopicDetail","interesting_read");//文章详情行为

		private String methodName;
	    private String intestringType;

	    private UserActionEnum(String methodName,String intestringType) {
	        this.methodName = methodName;
	        this.intestringType = intestringType;
	    }

	    public String getMethodName() {
			return methodName;
		}



		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}



		public String getIntestringType() {
			return intestringType;
		}



		public void setIntestringType(String intestringType) {
			this.intestringType = intestringType;
		}



		public static void main(String[] args) {

	        for (UserActionEnum value : UserActionEnum.values()) {
	            System.err.println(value.getMethodName());
	            System.err.println(value.getIntestringType());

	        }


	    }
	

}
