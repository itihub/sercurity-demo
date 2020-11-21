#自定义图形验证码规则
###实现说明
    继承 ValidateCodeGenerator.class
    @Override generateImageCode() 方法，书写自定义验证码规则
    实现类加入 @Component("imageCodeGenerator") 注解