library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity inverter is 
	
	port ( I : in  std_logic;
		   O : out std_logic );	
		   
end entity;

architecture arch of inverter is
begin

	O <= not I after 2ns;

end architecture;
			