fcountry = open('countrycodes.txt', 'r')
countrycodes = {}
for line in fcountry:
	line.strip();
	countrycodes[line.split(':')[1]]=line.split(':')[0]
	print(line+'\n')

fcountry.close()

fcbyr = open('cbyr.txt', 'r')
fout = open('out.txt', 'w')
region = "null"
for line in fcbyr: 
	line.strip();
	parts = line.split(':')
	if (parts[0] == "Region"):
		region = parts[1];
	else:
		if (parts[1] in countrycodes):
			fout.write(parts[1].rstrip()+":"+countrycodes[parts[1]]+":"+region)
	
fout.close()
