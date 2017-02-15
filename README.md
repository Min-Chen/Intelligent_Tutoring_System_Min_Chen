# Moving_Average_Min_Chen

- Build a `sliding window` of the `last 15 datapoints` of the emotion joy.

- For each window of the last 15 datapoints, calculate the mean. If the window is not full, do not
calculate a mean.

- Set a `threshold` between the range of 0-100 (e.g. 40). When the mean is greater than or equal
to the threshold, carry out a function, such as print the line `“You have reached the threshold”`.

- Instead of calculating the moving average of the last 15 datapoints, calculate
the moving average of the `last 15 seconds` (hint: use the `timestamp` of each datapoint).
