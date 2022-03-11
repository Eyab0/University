library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity twos_complement is
  port (
  A     : in std_logic_vector (7 downto 0);
  neg_A : out std_logic_vector(7 downto 0));
end entity;

architecture arch of twos_complement is
  signal not_A : std_logic_vector(7 downto 0);

begin

  GEN_NOT : for jj in 0 to 7 generate
    not_gen : inverter port map(A(jj), not_A(jj));
  end generate GEN_NOT;
  inc_1 : entity work.carry_look_ahead
  port map(
    A     => not_A,
    B     => "00000001",
    C_in  => '0',
    S     => neg_A,
    C_out => open
  );
  
end architecture;