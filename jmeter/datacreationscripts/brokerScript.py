
for i in range (0, 100):
	if i < 10:
		brokerCode = "B10{}".format(i)

	elif i >= 10 and i < 100:
		brokerCode = "B1{}".format(i)

	brokerIban = "BK12{}".format(i+1+202)
	brokerName = "Joy of Travelling"
	line = "{},{},{}".format(brokerCode, brokerName, brokerIban)
	print(line)
