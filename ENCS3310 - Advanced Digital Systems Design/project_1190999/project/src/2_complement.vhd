LIBRARY IEEE;
LIBRARY work;
USE work.gate_package.ALL;
USE IEEE.std_logic_1164.ALL;
USE IEEE.numeric_std.ALL;
USE IEEE.std_logic_unsigned.ALL;

ENTITY twos_complement IS
  PORT (
  A     : IN std_logic_vector (7 DOWNTO 0);
  neg_A : OUT std_logic_vector(7 DOWNTO 0));
END ENTITY;

ARCHITECTURE arch OF twos_complement IS
  SIGNAL not_A : std_logic_vector(7 DOWNTO 0);

BEGIN

  GEN_NOT : FOR jj IN 0 TO 7 GENERATE
    not_gen : inverter PORT MAP(A(jj), not_A(jj));
  END GENERATE GEN_NOT;
  inc_1 : ENTITY work.carry_look_ahead
  PORT MAP(
    A     => not_A,
    B     => "00000001",
    C_in  => '0',
    S     => neg_A,
    C_out => OPEN
  );
  
END ARCHITECTURE;