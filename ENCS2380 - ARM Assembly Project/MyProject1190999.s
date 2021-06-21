	AREA MyProject1190999,CODE,READONLY
	ENTRY
			MOV R10,#0
			
			; Name : Eyab Ghifari
			; ID : 1190999
			; my method is number 5 
			; (999 mod 5 )+ 1 = 5
			
MyName		DCB	    "Eyab",0 

			 
			ADR		R0,MyName          ; put a pointer(address) of MyName String in R0 
			LDR     R2,=0x40000000     ; Address in memory to point to the Encrypted string
			
StringToEncryption		               ;will take MyName as input and will encrypt it and put it in R2 Address 
			LDRB	R1,[R0]		       ;loads a byte from R0 Address into R1
			CMP		R1,#0		       ;chech if it reach the end of the string | compare between R0 and 0
			BEQ		ExitEncryption     ;Branch if Zero flag = 0 that mean it rech the end of the string 		
			EOR		R1,#3		       ;make XOR of the first and second bits , 00000011 -> 1 XOR b = b' -> 0 XOR b = b 
			STRB	R1,[R2]		       ;takes a byte of data from R1 and stores it to R2 Address 
			ADD		R0,#1		       ;increment R0 by 1 -> move 1 byte
			ADD		R2,#1		       ;increment R0 by 1 -> move 1 byte
			B		StringToEncryption ;Keep Looping until reach end of the string 
ExitEncryption                         ; exit from the Encryption	
			
			
			
			LDR         R0,=0x40000000 ;Address in memory to point to the Encrypted string
			LDR         R2,=0x40000FFF ;Address in memory to point to the Decrypted string
			
			
StringToDecryption		               ;will take R0 as input and will decrypt it and put it in R2 Address 
			LDRB	R1,[R0]		       ;loads a byte from R0 Address into R1
			CMP		R1,#0			   ;chech if it reach the end of the string | compare between R0 and 0
			BEQ		ExitDecryption	   ;Branch if Zero flag = 0 that mean it rech the end of the string 			
			EOR		R1,#3		       ;make XOR of the first and second bits , 00000011 -> 1 XOR b = b' -> 0 XOR b = b 
			STRB	R1,[R2]		       ;takes a byte of data from R1 and stores it to R2 Address 
			ADD		R0,#1		       ;increment R0 by 1 -> move 1 byte
			ADD		R2,#1		       ;increment R0 by 1 -> move 1 byte
			B		StringToDecryption ;Keep Looping until reach end of the string 
ExitDecryption						   ;exit from the Encryption	
			
			END		                   ;The End of the Program 
			
			
			
		