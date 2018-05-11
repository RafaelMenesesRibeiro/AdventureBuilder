
import datetime
import random

date = datetime.date(2017,5,2)

for i in range (0, 100):
	if i < 10:
		brokerCode = "B10{}".format(i)
		clientNif = "21111100{}".format(i)
		adventureCode = "B10{}{}".format(i, 1)

	elif i >= 10 < 100:
		brokerCode = "B1{}".format(i)
		clientNif = "2111110{}".format(i)
		adventureCode = "B1{}{}".format(i, 1)

	begin = date + datetime.timedelta(days=1)
	end = date + datetime.timedelta(days=2)
	date = date + datetime.timedelta(days=2)

	margin = "{0:.2f}".format(random.uniform(0.1, 1.0))
	age = random.randint(10, 100)

	line1 = "{},{},{},{},{}".format(brokerCode, begin, end, margin, age)
	print(line1)
