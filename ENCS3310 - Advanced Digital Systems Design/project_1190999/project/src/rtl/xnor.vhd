library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity xnor_gate is 
	
	port ( I_0 : in  std_logic;
		   I_1 : in  std_logic; 	
		   O   : out std_logic );	
		   
end entity;

architecture arch of xnor_gate is
begin

	O <= I_0 xnor I_1 after 9ns;

end architecture;