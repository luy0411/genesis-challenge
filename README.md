# genesis-challenge

The goal of this challenge is to create a Java program that, given a collection of cryptocurrency assets with their positions, should simultaneously search for and update the latest prices for each asset through the Coincap API. The program should return the updated total financial value of the wallet, as well as information about the performance of the assets.

The input will be a CSV file representing the wallet, with columns for symbol, quantity, and price. The output will be a line with information about the performance, including the total value of the wallet in dollars, the best-performing asset, the percentage performance of that asset, the worst-performing asset, and the percentage performance of that asset. All values should be rounded to two decimal places and follow the HALF_UP rule.

Here are some technical details:

- You need to use Java 11+ and build the project with Maven.

- It's mandatory to write unit tests, which may or may not use API mocks.

- Asset prices must be obtained simultaneously in groups of three, and never in a single thread (unless there is only one asset in the wallet).

- You need to use the Coincap API to fetch the necessary information. Here's an example link: https://docs.coincap.io/#89deffa0-ab03-4e0a-8d92-637a857d2c91

- An example of input and output is given in the challenge original text.
