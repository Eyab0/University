library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity nand_gate is 
	
	port ( I_0 : in  std_logic;
		   I_1 : in  std_logic; 	
		   O   : out std_logic );	
		   
end entity;

architecture arch of nand_gate is
begin

	O <= I_0 nand I_1 after 5ns;

end architecture;