# Final-Year-Project

  Sign-App, an app that aims to enable people an easy and free way to learn sign language. There is a definite opening and demand for an app like this as there are no good, free sign language teacher apps in the market. The app will provide resources such as quizzes, basic sign language phrases, a user’s own pictionary that they can expand themselves, and the ability to download other people’s pictionary and image recognition tool to compliment the quiz functionality. The main goal of the project is to enable users to learn sign language in their own time. With the app being a teaching tool, it needs to motivate users and interest the audience enough to come back. The app has potential and room to grow with more functionalities down the line of the software development life cycle. In the end, the user should expect the application to hold a database with their own signs, quizzes to test their knowledge, and a free flowing app that is easy to use.


This prototype is written in Python, using Jupyter Notebook. This means that I was able to partition the code per block.
This first block of code involves importing the libraries used in the prototype.
The second block involves loading the data from the directories. The data is also split in two, 80% training and 20% testing.
![loaddata](https://user-images.githubusercontent.com/23058507/49383781-75f07e00-f711-11e8-8e63-b0b5eda55fca.png)

The third block shuffles the loaded data to give a more consistent result.
The fourth block is a helper function. This function saves the progress per epoch finished.
The last block uses and calls a pre-trained algorithm VGG16 to train the model.
<img width="735" alt="knn results" src="https://user-images.githubusercontent.com/23058507/49383790-79840500-f711-11e8-995f-13ddc3598d16.PNG">

The dataset used is from Kaggle: https://www.kaggle.com/grassknoted/asl-alphabet

The book, "Deep Learning For Computer Vision by Dr. Adrian Rosebrock", helped me with the basic understanding in Machine Learning and gave examples in how to code with Keras and TensorFlow.




