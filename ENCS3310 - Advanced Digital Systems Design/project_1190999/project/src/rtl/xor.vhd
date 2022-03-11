library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity xor_gate is 
	
	port ( I_0 : in  std_logic;
		   I_1 : in  std_logic; 	
		   O   : out std_logic );	
		   
end entity;

architecture arch of xor_gate is
begin

	O <= I_0 xor I_1 after 12ns;

end architecture;