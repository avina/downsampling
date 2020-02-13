# 关于本项目
时序数据降采样算法（LTOB, LTTB, LTD, OSI-PI Plot 等）的Java实现。<br/>
[README - English](https://github.com/avina/downsampling/blob/master/README_en.md)
# 算法
* LTOB: Largest-Triangel-One-Bucket
* LTTB: Largest-Triangel-Three-Bucket
* LTD: Largest-Triangel-Dynamic
* PIPLOT: OSI Soft PI 趋势图的降采样算法
* MAXMIN: 取最大、最小值的降采样算法

最大三角形系列算法的作者为Sveinn Steinarsson.<br/>
LTD算法的实现与原版有区别:  动态桶大小迭代的次数为 datasize / threshold / 10 且上限500.

# 用法
使用 `DSAlgorithms` 类获得算法的实例。
## 实现降采样：
```
  List<Event> rawData = getData(); // Read data.
  DownSamplingAlgorithm algorithm = DSAlgorithms.LTTB // get a algorithm.
  List<Event> downsampled = algorithm.process(rawData, threshold); // do downsampling
```
## 混合算法：
```
  MixedAlgorithm mixed = new MixedAlgorithm();
  mixed.add(DSAlgorithms.LTTB, 1); // add LTTB with threshold * 1
  mixed.add(new TimeGapAlgorithm(), 0.1); // add TG with threshold * 0.1
  List<Event> downsampled = mixed.process(rawData, threshold); // do downsampling
```
# 例子
运行test包下的 `DownsampleTest`类
