# About this project
Downsampling algorithms for time series data（LTOB, LTTB, LTD, OSI-PI Plot, etc.）Implemented in Java.
# Algorithms
* LTOB: Largest-Triangel-One-Bucket.
* LTTB: Largest-Triangel-Three-Bucket.
* LTD: Largest-Triangel-Dynamic.
* PIPLOT: OSI Soft PI PlotValues algorithm.
* MAXMIN: Algorithm that picks maximum and minimum value in buckets.

LT algorithms are designed by Sveinn Steinarsson.<br/>
LTD implementation is different from original:  number of iterations in this implementation is datasize / threshold / 10 and limited to 500.

# How to use
Use class `DSAlgorithms` to get a downsampling algorithm.
## do downsampling
```
  List<Event> rawData = getData(); // Read data.
  DownSamplingAlgorithm algorithm = DSAlgorithms.LTTB // get a algorithm.
  List<Event> downsampled = algorithm.process(rawData, threshold); // do downsampling
```
## mix algorithms
```
  MixedAlgorithm mixed = new MixedAlgorithm();
  mixed.add(DSAlgorithms.LTTB, 1); // add LTTB with threshold * 1
  mixed.add(new TimeGapAlgorithm(), 0.1); // add TG with threshold * 0.1
  List<Event> downsampled = mixed.process(rawData, threshold); // do downsampling
```
