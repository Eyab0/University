index=1000
for i in {1..50} 
	do
	printf "Year/semester ; Courses with grades\n2020-2021/1 ; encs333 88,enee131 80,encs555 90\n" >> 119${index}.txt
	index=$((index+1))
	done
