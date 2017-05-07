# Intelligent Tutoring System

# How to install
1. Install HCI_Sentiment_Analysis_Min_Chen by following the readme file at this link: https://github.com/Min-Chen/HCI_Sentiment_Analysis_Min_Chen/tree/master.
2. Download Android Studio at https://developer.android.com/studio/index.html and install it locally.
3. Download this facial expression detection module by running the command ```git clone https://github.com/Min-Chen/Intelligent_Tutoring_System_Min_Chen.git```.


# Analyze Facial Expressions
- Use `Affectiva` to track user's facial expressions in real time.
- `frown` presents a tendency of negative emotion; `smile` presents a tendency of positive emotion.
- Build a `sliding window` of the `last 15 datapoints` of the emotion `joy` and expression `frown`.

- For each window of the last 15 datapoints, calculate the mean. If the window is not full, do not
calculate a mean.

- Set a `threshold` between the range of 0-100 (e.g. 40). When the mean is greater than or equal
to the threshold, carry out a function, print the line `“You have reached the threshold of confusion because you frowned for a while”`.

- Alternaltive approach: Instead of calculating the moving average of the last 15 datapoints, calculate
the moving average of the `last 15 seconds` (hint: use the `timestamp` of each datapoint).

# Analyze Text Input by Sentiment Analysis
- User input texts using command line in android studio.

- The Intelligent Tutoring System takes user's text input, analyzes it, and outputs whether the user's sentiment is negative or positive based on his/her text input.

# Multimodal Approach to Predict Emotions
- If no text input, use facial expression result;
- If facial expression is smiling, use facial expression result;
- If facial expression is not frowning and not smiling, use text sentiment analysis;
- Otherwise, normalize the two analysis results,
 and `result = 70% * text sentiment analysis + 30% * facial expression analysis`.

# Adaptations
- Give appropriate help to the user when user's emotion/sentiment is negative.
- Leave them along when his/her emotion/sentiment is positive.

