echo "Final Exam"
while true
do
echo "please enter a number :"
read num
sum=0
f=$num
[[ $num =~ ^[0-9]+$ ]] || {
        printf "error ,only numbers\n"
        continue
    }

for((i=0;i<${#num};i++))
	 do 
	 	tmp=${num:i:1}
		f=`expr $tmp \* $tmp \* $tmp`
		sum=`expr $sum + $f`
		echo "${num:i:1} = $f"
		#echo $sum 
	done

echo $sum

if [ $sum = $num ] 
then 
	echo "the number eqal the sum of of digits cube "
	exit 0
fi

echo "the number dose not eqal the sum of of digits cube "
exit 1
done