自定义View流程


重写构造函数--->初始化一些操作，，并获取自定义属性
     |
 onMeasure: 测量View自身的大小（受父布局影响）
     |
 onSizeChange:View大小确定回调
     |
 onLayout--->确定自View的位置参数，，，可通过requestLayout主动触发
     |
 onDraw---->绘制，，，调用invalidate触发