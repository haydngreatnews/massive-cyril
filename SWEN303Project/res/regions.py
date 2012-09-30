fcbyr = open('cbyr.txt', 'r')
fout = open('reg.txt', 'w')
for line in fcbyr: 
	line.strip();
	parts = line.split(':')
	if (parts[0] == "Region"):
		fout.write(parts[1].replace(' ','_')+",");
fout.close()
