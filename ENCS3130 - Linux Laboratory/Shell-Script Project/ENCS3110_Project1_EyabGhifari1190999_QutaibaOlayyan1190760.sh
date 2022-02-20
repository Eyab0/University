#Eyab Ghifari - 1190999
#Qutaiba Olayyan - 1190760


ShowMenu() {
    #function to pritn the menu
    bold=$(tput bold)
    echo -e "\n\n \e[40m\t\t${bold}    Contact Management System     ${bold}\e[0m"
    printf "\n\n"
    printf "\e[40m\t\t▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n\e[0m"
    printf "\e[40m\t\t▌          <<Menu>>              ▐\n\e[0m"
    printf "\e[40m\t\t▌                                ▐\n\e[0m"
    printf "\e[40m\t\t▌[1] Add a new Contact           ▐\n\e[0m"
    printf "\e[40m\t\t▌[2] List all Contacts           ▐\n\e[0m"
    printf "\e[40m\t\t▌[3] Search for contact          ▐\n\e[0m"
    printf "\e[40m\t\t▌[4] Edit a Contact              ▐\n\e[0m"
    printf "\e[40m\t\t▌[5] Delete a Contact            ▐\n\e[0m"
    printf "\e[40m\t\t▌[0] Exit                        ▐\n\e[0m"
    printf "\e[40m\t\t▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
    printf "\e[40m\t\tEnter Your Choice >> \e[0m"
}

Exit() {

    #check if 'figlet' library is installed or not
    if is_installed "figlet"; then
        clear
        GREEN="\e[1;92m"
        printf "${GREEN}"
        printf "\n\n\t\t\t\tDone By : \n\n\n"
        figlet -c '     Eyab                                                  ' -f big
        figlet -c '     1190999                                                  ' -f big
        figlet -c '        and                                                         ' -f big

        figlet -c ' Qutaiba                                                ' -f big
        figlet -c ' 1190760                                                ' -f big

        printf "\n\n\n\n"
        exit 0
    else
        clear
        GREEN="\e[1;7;92m"
        printf "${GREEN}"
        printf "\n\t\t   Done By :  \n\t\tEyab & Qutaiba\n\n\n"
        exit 0

    fi

}

function is_installed() {
    #checker if any library is installed or not
    if [ -n $(dpkg -l | awk "/^ii  $1/")]; then
        return 1
    fi
    return 0
}
getFileDate() {
    #read date from file and store it in arrays to print it

    #get the first line from the file
    read -r firstline <${fileName}

    # reading each line
    while read line; do
        #delimiter  to split each line
        IFS=','
        #put splited data into array
        read -a data <<<"$line"
        #used for debug
        #echo "1 : ${data[0]}"
        #echo "2 : ${data[1]}"
        #echo "Line No. $size : $line"\

        #put each value to it own from data array into new arrays
        firstName[$size]=${data[0]}
        lastName[$size]=${data[1]}
        phoneNumber[$size]=${data[2]}
        email[$size]=${data[3]}

        #used for debug
        #printf "name : ${firstName[$size]} ${lastName[$size]} \n"
        #printf "phone : ${phoneNumber[$size]}\n"
        #printf "email : ${email[$size]}\n"

        size=$((size + 1))
    done < <(tail -n +2 <$fileName)
    #printf "\nsize of n : $size \n"
}

clear
echo
printf "\n\n\e[1;42m\t\tWelcome to Contact Management System \e[0m \n\n\n"
printf "\e[1;40m>>please enter the contact date file name : \e[0m"
read fileName

#check if the file is exist or ordinary file
while [ ! -e $fileName -o ! -f $fileName ]; do
    printf "\e[1;7;31mError :\n >>file not exist  \nor\n >>not ordinary file\n\e[0m"
    printf "\e[1;40m\n>>please enter the contact date file name : \e[0m"
    read fileName
done

while :; do
    #decaler arrays to store data into it
    size=0
    declare -a firstName
    declare -a lastName
    declare -a phoneNumber
    declare -a email
    firstline=""

    #call file data function to read the data from the file
    getFileDate

    clear
    ShowMenu

    read choice
    #check if the input choice is integer
    [[ $choice =~ ^[0-9]+$ ]] || {
        printf "\e[1;7;31minvalid Choice , Try again \nchoose in range [0-5] only\e[0m"
        sleep 1.2
        continue
    }

    #switch case statment
    case ${choice} in
    1)
        clear
        info="-"
        newfirstname=""
        newLastName=" "
        num=""
        newemail=" "
        while [[ "$info" != "q" ]]; do

            printf "
            1)First Name\e[1;31m*\e[0m \n
            2)Last Name\n
            3)Phone Number\e[1;31m*\e[0m\n
            4)Email\n
            q)Exit\n
            \e[1;31m*\e[0m required\n
            Enter the info. number you want to add : 
            "
            read info
            #read input choice\
            case ${info} in
            1)

                printf "Enter First Name: \n"
                read newfirstname

                ;;
            2)

                printf " Enter Last Name: \n"
                read newLastName
                lastname=$newLastName

                ;;
            3)

                printf "How much phone you want to enter \n"
                read count
                for ((i = 0; i < count; i++)); do

                    while true; do

                        printf " enter the %d phone \n" $((i + 1))
                        read n
                        #check if the phone number is contain only numbers with length of 9 or 10 numbers
                        if echo $n | grep "[0-9]\{9,10\}" >/dev/null; then
                            if [ $i -eq $((count - 1)) ]; then
                                num+=$n
                                break
                            else
                                num+=$n
                                num+=";"
                                break
                            fi
                        else
                            printf " \e[1;31invalid number \n> should enter correct number with 9 - 10 digits\e[0m \n"
                            continue
                        fi

                    done
                done

                ;;

            4)

                while true; do
                    printf " enter the email \n"
                    read em
                    #check if the email address  contain @ symbol
                    if echo $em | grep ".*@.*" >/dev/null; then
                        newemail=$em
                        break
                    else
                        printf "you should enter correct email\n"
                        continue
                    fi

                done

                ;;

            q)
                #done adding
                break
                ;;
            *)
                printf "\e[1;7;31minvalid Choice , Try again\nchoose in range [1-4]  \e[0m"
                sleep 1.2
                continue
                ;;

            esac

        done

        #echo $newfirstname
        #echo $newLastName
        #echo $num
        #echo $newemail
        #check if the user does not add first name of phone number
        if [ "$newfirstname" == "" -o "$num" == "" ]; then
            printf "\e[1;7;31mError :\n >>first name not exist  \nor\n >>phone number not exist\n\e[0m"
        else
            #add new info. to file
            printf "%s,%s,%s,%s\n" $newfirstname $newLastName $num $newemail >>$fileName
        fi

        printf "\t\tPress any key to back to menu..."
        read go

        ;;
    2)
        clear

        sed "1d" $fileName >temp   #remove first line
        tr ',' ' ' <temp >temp1    #split data into temp file
        tr -s ' ' ' ' <temp1 >temp #replace each sequence of a repeated ' '
        #ask user the way of sorting
        printf " sort by \nfirst name: 1\nlast name: 2\n"
        read so
        if [ $so -eq 1 ]; then
            sort -k 1 <temp >temp1 #sorting by first filed
            cat temp1 >temp        #put sorted data into temp file

            #ask user what field  want to show
            printf " 
            1)First Name\n
            2)Last Name\n
            3)Phone Number\n
            4)Email\n
            5)All fileds \n
           Enter the field. numbers separated by space or comma you want to show it: 
            "

            read -a fd

            if [ ${fd[0]} == "5" ]; then
                #print all fileds
                clear
                printf "\n\n\e[32m\t\t▛▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n\e[0m"
                printf "\e[32m\t\t▌# ▌\e[0m\e[1;42mFirst Name\e[0m  \e[32m▌\e[0m \e[1;42mLast Name \e[0m \e[32m▌\e[0m \e[1;42mPhone Number\e[0m  \e[32m▌\e[0m      \e[1;42mEmail\e[0m                 \e[32m▐\e[0m\n\e[0m"
                printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
                for ((i = 0; i < size; i++)); do
                    printf "\e[32m\t\t▌\e[0m\e[1;97m%-2d\e[0m\e[32m▌\e[1;31m%-10s\e[0m  \e[32m▌\e[0m \e[1;31m%-9s \e[0m \e[32m▌\e[0m \e[1;31m%-12s\e[0m  \e[32m▌\e[0m\e[1;31m %-20s\e[0m\e[32m       ▐\e[0m\n\e[0m" $(($i + 1)) "${firstName[$i]}" "${lastName[$i]}" "${phoneNumber[$i]}" "${email[$i]}"
                    #printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
                done
                printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
            else
                fields=$(IFS=, echo "${fd[@]}")  #if the it separated by a comma
                cut -d " " -f"${fields[@]}" temp #show needed fileds only
            fi

        elif
            [ $so -eq 2 ]
        then
            sort -k 2 <temp >temp1 #sorting by second filed
            cat temp1 >temp        #put sorted data into temp file
            #ask user what field  want to show
            printf "
            1)First Name\n
            2)Last Name\n
            3)Phone Number\n
            4)Email\n
             5)All Felids \n
           Enter the field. numbers separated by space or comma you want to show it: 
            "

            read -a fd
            if [ ${fd[0]} == "5" ]; then
                #print all fileds
                clear
                printf "\n\n\e[32m\t\t▛▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n\e[0m"
                printf "\e[32m\t\t▌# ▌\e[0m\e[1;42mFirst Name\e[0m  \e[32m▌\e[0m \e[1;42mLast Name \e[0m \e[32m▌\e[0m \e[1;42mPhone Number\e[0m  \e[32m▌\e[0m      \e[1;42mEmail\e[0m                 \e[32m▐\e[0m\n\e[0m"
                printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
                for ((i = 0; i < size; i++)); do
                    printf "\e[32m\t\t▌\e[0m\e[1;97m%-2d\e[0m\e[32m▌\e[1;31m%-10s\e[0m  \e[32m▌\e[0m \e[1;31m%-9s \e[0m \e[32m▌\e[0m \e[1;31m%-12s\e[0m  \e[32m▌\e[0m\e[1;31m %-20s\e[0m\e[32m       ▐\e[0m\n\e[0m" $(($i + 1)) "${firstName[$i]}" "${lastName[$i]}" "${phoneNumber[$i]}" "${email[$i]}"
                    #printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
                done
                printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
            else
                fields=$(IFS=, echo "${fd[@]}")  #if the it separated by a comma
                cut -d " " -f"${fields[@]}" temp #show needed fileds only
            fi

        else

            printf " wrong choice [1-2] \n"

        fi

        printf "\n\n\t\tPress any key to back to menu..."
        read go
        ;;
    3)
        clear
        f1=''
        f2=''
        f3=''
        f4=''
        printf "
            1)First Name\n
            2)Last Name\n
            3)Phone Number\n
            4)Email\n
           Enter the field number you want to search in it: 
            "
        #ask user to which field want to search in it
        read f
        if [ $f == "1" ]; then
            printf " enter the first name:\n"
            read f1
        elif [ $f == "2" ]; then
            printf " enter the last name:\n"
            read f2
        elif [ $f == "3" ]; then
            printf " enter the number:\n"
            read f3
        elif [ $f == "4" ]; then
            printf " enter the email:\n"
            read f4
        else
            echo "invalid field \n"
        fi

        #search for the field nedded
        grep .*"$f1".*.*"$f2".*.*"$f3".*.*"$f4".* $fileName >temp || echo " It not exist "
        cat temp

        printf "\t\tPress any key to back to menu..."
        read go
        ;;
    4)
        clear
        clear
        printf "\n\n\e[32m\t\t▛▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n\e[0m"
        printf "\e[32m\t\t▌# ▌\e[0m\e[1;42mFirst Name\e[0m  \e[32m▌\e[0m \e[1;42mLast Name \e[0m \e[32m▌\e[0m \e[1;42mPhone Number\e[0m  \e[32m▌\e[0m      \e[1;42mEmail\e[0m                 \e[32m▐\e[0m\n\e[0m"
        printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        for ((i = 0; i < size; i++)); do
            printf "\e[32m\t\t▌\e[0m\e[1;97m%-2d\e[0m\e[32m▌\e[1;31m%-10s\e[0m  \e[32m▌\e[0m \e[1;31m%-9s \e[0m \e[32m▌\e[0m \e[1;31m%-12s\e[0m  \e[32m▌\e[0m\e[1;31m %-20s\e[0m\e[32m       ▐\e[0m\n\e[0m" $(($i + 1)) "${firstName[$i]}" "${lastName[$i]}" "${phoneNumber[$i]}" "${email[$i]}"
            #printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        done
        printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        printf "Enter # number you want to change it info: "
        read l
        ch=""
        while [[ "$ch" != "q" ]]; do
            printf "
            1)First Name\n
            2)Last Name\n
            3)Phone Number\n
            4)Email\n
            q)Exit\n
            enter info number you want to change it: 
            "
            #ask user to which field want to edit it
            read ch
            sed "1d" $fileName >temp   #remove first line
            tr ',' ' ' <temp >temp1    #split data into temp file
            tr -s ' ' ' ' <temp1 >temp #replace each sequence of a repeated ' '
            #editting done
            if [ "$ch" != "q" ]; then
                o=$(echo $(sed -n $((l))p temp) | cut -d' ' -f$((ch))) #get the field to repalce it after get the new value
                (echo $(sed -n $((l))p temp) | tr ' ' '\12' >temp3)
            fi

            new=""
            if [ $ch == 3 ]; then

                printf "How much phone u want to enter \n"
                read count
                for ((i = 0; i < count; i++)); do

                    while true; do

                        printf " enter the %d phone \n" $((i + 1))
                        read n
                        #check if the phone number is contain only numbers with length of 9 or 10 numbers

                        if echo $n | grep "[0-9]\{9,10\}" >/dev/null; then
                            if [ $i -eq $((count - 1)) ]; then
                                new+=$n
                                break
                            else
                                new+=$n
                                new+=";"
                                break
                            fi
                        else
                            printf " u should enter correct number with 9 digits \n"
                            continue
                        fi

                    done
                done

            elif [ $ch == 4 ]; then

                while true; do
                    printf " enter the email \n"
                    read new
                    #check if the email address  contain @ symbol
                    if echo $new | grep ".*@.*" >/dev/null; then
                        break
                    else
                        printf "you should enter correct email\n"
                        continue
                    fi

                done
            elif [ $ch == "q" ]; then
                break
            else
                printf "please enter the new value for the field\n"
                read new
            fi
            sed -i "$ch s/$o/$new/g" temp3                         #replace the old value with the new value
            sed -i $((l + 1))d $fileName                           #delete the line from the file
            sed -i "$((l + 1))i $(paste -s -d',' temp3)" $fileName #insert the new value of the line into the file
            #cat $fileName
        done
        printf "\t\tPress any key to back to menu..."
        read go
        ;;
    5)
        clear
        clear
        printf "\n\n\e[32m\t\t▛▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▛▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▜\n\e[0m"
        printf "\e[32m\t\t▌# ▌\e[0m\e[1;42mFirst Name\e[0m  \e[32m▌\e[0m \e[1;42mLast Name \e[0m \e[32m▌\e[0m \e[1;42mPhone Number\e[0m  \e[32m▌\e[0m      \e[1;42mEmail\e[0m                 \e[32m▐\e[0m\n\e[0m"
        printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        for ((i = 0; i < size; i++)); do
            printf "\e[32m\t\t▌\e[0m\e[1;97m%-2d\e[0m\e[32m▌\e[1;31m%-10s\e[0m  \e[32m▌\e[0m \e[1;31m%-9s \e[0m \e[32m▌\e[0m \e[1;31m%-12s\e[0m  \e[32m▌\e[0m\e[1;31m %-20s\e[0m\e[32m       ▐\e[0m\n\e[0m" $(($i + 1)) "${firstName[$i]}" "${lastName[$i]}" "${phoneNumber[$i]}" "${email[$i]}"
            #printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        done
        printf "\e[32m\t\t▙▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▙▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▟\n\e[0m"
        printf "Enter # number you want to change it info: "
        read del
        del=$((del + 1))
        sed -i ${del}d $fileName #delete the wanted line from the file
        printf "\t\tPress any key to back to menu..."
        read go
        ;;
    0)
        Exit
        ;;
    *)
        printf "\e[1;7;31minvalid Choice , Try again\nchoose in range [0-5] only \e[0m"
        sleep 1.2
        continue
        ;;
    esac

done
