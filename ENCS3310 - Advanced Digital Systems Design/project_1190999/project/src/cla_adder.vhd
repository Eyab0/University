library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;
entity carry_look_ahead is
  port (
    A     : in std_logic_vector (7 downto 0);
    B     : in std_logic_vector (7 downto 0);
    C_in  : in std_logic;
    S     : out std_logic_vector (7 downto 0);
    C_out : out std_logic);
end entity;

architecture arch of carry_look_ahead is

  component partial_full_adder
    port (
      A : in std_logic;
      B : in std_logic;
      C : in std_logic;
      P : out std_logic;
      S : out std_logic;
      G : out std_logic);
  end component;

  signal C1, C2, C3, C4, C5, C6, C7 : std_logic;
  signal P, G                       : std_logic_vector(7 downto 0);
  signal wire                       : std_logic_vector(37 downto 0);
begin
  --Port Mapping

  --for 1's complement	
  PFA0 : partial_full_adder port map(A => A(0), B => B(0), C => C_in, P => P(0), S => S(0), G => G(0));
  PFA1 : partial_full_adder port map(A => A(1), B => B(1), C => C1, P => P(1), S => S(1), G => G(1));
  PFA2 : partial_full_adder port map(A => A(2), B => B(2), C => C2, P => P(2), S => S(2), G => G(2));
  PFA3 : partial_full_adder port map(A => A(3), B => B(3), C => C3, P => P(3), S => S(3), G => G(3));
  PFA4 : partial_full_adder port map(A => A(4), B => B(4), C => C4, P => P(4), S => S(4), G => G(4));
  PFA5 : partial_full_adder port map(A => A(5), B => B(5), C => C5, P => P(5), S => S(5), G => G(5));
  PFA6 : partial_full_adder port map(A => A(6), B => B(6), C => C6, P => P(6), S => S(6), G => G(6));
  PFA7 : partial_full_adder port map(A => A(7), B => B(7), C => C7, P => P(7), S => S(7), G => G(7));

  --CLA Circuit Functions
  -- C1 <= G(0) OR (P(0) AND C_in);
  AND0 : and_gate port map(P(0), C_in, wire(0));
  OR0  : or_gate port map(wire(0), G(0), C1);

  -- C2 <= G(1) OR (P(1) AND G(0)) OR (P(1) AND P(0) AND C_in);
  AND1     : and_gate port map(P(1), G(0), wire(1));
  AND_GEN0 : and_generic generic map(3) port map(P(1), P(0), C_in, '0', '0', '0', '0', '0', wire(2));
  OR_GEN0  : or_generic generic map(3) port map(wire(1), wire(2), G(1), '0', '0', '0', '0', '0', C2);

  -- C3 <= G(2) OR (P(2) AND G(1)) OR (P(2) AND P(1) AND G(0)) OR (P(2) AND P(1) AND P(0) AND C_in);
  AND2     : and_gate port map(P(2), G(1), wire(3));
  AND_GEN1 : and_generic generic map(3) port map(P(2), P(1), G(0), '0', '0', '0', '0', '0', wire(4));
  AND_GEN2 : and_generic generic map(4) port map(P(2), P(1), P(0), C_in, '0', '0', '0', '0', wire(5));
  OR_GEN1  : or_generic generic map(4) port map(wire(3), wire(4), wire(5), G(2), '0', '0', '0', '0', C3);
  -- C4 <= G(3) OR (P(3) AND G(2)) OR (P(3) AND P(2) AND G(1)) OR (P(3) AND P(2) AND P(1) AND G(0)) OR (P(3) AND P(2) AND P(1) AND P(0) AND C_in);
  AND3     : and_gate port map(P(3), G(2), wire(6));
  AND_GEN3 : and_generic generic map(3) port map(P(3), P(2), G(1), '0', '0', '0', '0', '0', wire(7));
  AND_GEN4 : and_generic generic map(4) port map(P(3), P(2), P(1), G(0), '0', '0', '0', '0', wire(8));
  AND_GEN5 : and_generic generic map(5) port map(P(3), P(2), P(1), P(0), C_in, '0', '0', '0', wire(9));
  OR_GEN2  : or_generic generic map(5) port map(wire(6), wire(7), wire(8), wire(9), G(3), '0', '0', '0', C4);
  -- C5 <= G(4) OR (P(4) AND G(3)) OR (P(4) AND P(3) AND G(2)) OR (P(4) AND P(3) AND P(2) AND G(1)) OR (P(4) AND P(3) AND P(2) AND P(1) AND G(0)) OR (P(4) AND P(3) AND P(2) AND P(1) AND P(0) AND C_in);
  AND4     : and_gate port map(P(4), G(3), wire(10));
  AND_GEN6 : and_generic generic map(3) port map(P(4), P(3), G(2), '0', '0', '0', '0', '0', wire(11));
  AND_GEN7 : and_generic generic map(4) port map(P(4), P(3), P(2), G(1), '0', '0', '0', '0', wire(12));
  AND_GEN8 : and_generic generic map(5) port map(P(4), P(3), P(2), P(1), G(0), '0', '0', '0', wire(13));
  AND_GEN9 : and_generic generic map(6) port map(P(4), P(3), P(2), P(1), P(0), C_in, '0', '0', wire(14));
  OR_GEN3  : or_generic generic map(6) port map(wire(10), wire(11), wire(12), wire(13), wire(14), G(4), '0', '0', C5);
  -- C6 <= G(5) OR (P(5) AND G(4)) OR (P(5) AND P(4) AND G(3)) OR (P(5) AND P(4) AND P(3) AND G(2)) OR (P(5) AND P(4) AND P(3) AND P(2) AND G(1)) OR (P(5) AND P(4) AND P(3) AND G(2)) OR (P(5) AND P(4) AND P(3) AND P(2) AND G(1)) OR (P(5) AND P(4) AND P(3) AND P(2) AND P(1) AND G(0)) OR (P(5) AND P(4) AND P(3) AND P(2) AND P(1) AND P(0) AND C_in);
  AND5      : and_gate port map(P(5), G(4), wire(15));
  AND_GEN10 : and_generic generic map(3) port map(P(5), P(4), G(3), '0', '0', '0', '0', '0', wire(16));
  AND_GEN11 : and_generic generic map(4) port map(P(5), P(4), P(3), G(2), '0', '0', '0', '0', wire(17));
  AND_GEN12 : and_generic generic map(5) port map(P(5), P(4), P(3), P(2), G(1), '0', '0', '0', wire(18));
  AND_GEN13 : and_generic generic map(6) port map(P(5), P(4), P(3), P(2), P(1), G(0), '0', '0', wire(19));
  AND_GEN14 : and_generic generic map(7) port map(P(5), P(4), P(3), P(2), P(1), P(0), C_in, '0', wire(20));
  OR_GEN4   : or_generic generic map(7) port map(wire(15), wire(16), wire(17), wire(18), wire(19), wire(20), G(5), '0', C6);
  -- C7 <= G(6) OR (P(6) AND G(5)) OR (P(6) AND P(5) AND G(4)) OR (P(6) AND P(5) AND P(4) AND G(3)) OR (P(6) AND P(5) AND P(4) AND P(3) AND G(2)) OR (P(6) AND P(5) AND P(4) AND G(3)) OR (P(6) AND P(5) AND P(4) AND P(3) AND G(2)) OR (P(6) AND P(5) AND P(4) AND P(3) AND P(2) AND G(1)) OR (P(6) AND P(5) AND P(4) AND P(3) AND P(2) AND P(1) AND G(0)) OR (P(6) AND P(5) AND P(4) AND P(3) AND P(2) AND P(1) AND P(0) AND C_in) ;
  AND6      : and_gate port map(P(6), G(5), wire(21));
  AND_GEN15 : and_generic generic map(3) port map(P(6), P(5), G(4), '0', '0', '0', '0', '0', wire(22));
  AND_GEN16 : and_generic generic map(4) port map(P(6), P(5), P(4), G(3), '0', '0', '0', '0', wire(23));
  AND_GEN17 : and_generic generic map(5) port map(P(6), P(5), P(4), P(3), G(2), '0', '0', '0', wire(24));
  AND_GEN18 : and_generic generic map(6) port map(P(6), P(5), P(4), P(3), P(2), G(1), '0', '0', wire(25));
  AND_GEN19 : and_generic generic map(7) port map(P(6), P(5), P(4), P(3), P(2), P(1), G(0), '0', wire(26));
  AND_GEN20 : and_generic generic map(8) port map(P(6), P(5), P(4), P(3), P(2), P(1), P(0), C_in, wire(27));
  OR_GEN5   : or_generic generic map(8) port map(wire(21), wire(22), wire(23), wire(24), wire(25), wire(26), wire(27), G(6), C7);
  --
  AND7      : and_gate port map(P(7), G(6), wire(28));
  AND_GEN21 : and_generic generic map(3) port map(P(7), P(6), G(5), '0', '0', '0', '0', '0', wire(29));
  AND_GEN22 : and_generic generic map(4) port map(P(7), P(6), P(5), G(4), '0', '0', '0', '0', wire(30));
  AND_GEN23 : and_generic generic map(5) port map(P(7), P(6), P(5), P(4), G(3), '0', '0', '0', wire(31));
  AND_GEN24 : and_generic generic map(6) port map(P(7), P(6), P(5), P(4), P(3), G(2), '0', '0', wire(32));
  AND_GEN25 : and_generic generic map(7) port map(P(7), P(6), P(5), P(4), P(3), P(2), G(1), '0', wire(33));
  AND_GEN26 : and_generic generic map(8) port map(P(7), P(6), P(5), P(4), P(3), P(2), P(1), G(0), wire(34));
  AND_GEN27 : and_generic generic map(8) port map(P(7), P(6), P(5), P(4), P(3), P(2), P(1), P(0), wire(35));
  AND8      : and_gate port map(wire(35), C_in, wire(36));
  OR_GEN6   : or_generic generic map(8) port map(wire(28), wire(29), wire(30), wire(31), wire(32), wire(33), wire(34), wire(36), wire(37));
  OR_0      : or_gate port map(wire(37), G(7), C_out);

end architecture;