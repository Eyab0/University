library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;
entity magnitude_comparator_4bit is

  port (
    A : in std_logic_vector(3 downto 0);
    B : in std_logic_vector(3 downto 0);

    i_a_gt_b : in std_logic;
    i_a_lt_b : in std_logic;
    i_a_eq_b : in std_logic;
    a_gt_B   : out std_logic;
    a_eq_B   : out std_logic;
    a_lt_B   : out std_logic);
end entity;

architecture arch of magnitude_comparator_4bit is

  signal s, not_B, not_A         : std_logic_vector(3 downto 0);
  signal o_and0, o_and1, a7, b7  : std_logic;
  signal gate_O_and, gate_O_and1 : std_logic_vector(8 downto 0);
  signal gate_O_or, gate_O_or1   : std_logic_vector(1 downto 0);

  signal m_A_gt_B : std_logic;
  signal m_A_lt_B : std_logic;
  signal m_A_eq_B : std_logic;

  signal wire : std_logic_vector(1 downto 0);
begin

  

 

  

  -- A=B --

  XNOR0 : xnor_gate port map(I_0 => A(0), I_1 => B(0), O => s(0));
  XNOR1 : xnor_gate port map(I_0 => A(1), I_1 => B(1), O => s(1));
  XNOR2 : xnor_gate port map(I_0 => A(2), I_1 => B(2), O => s(2));
  XNOR3 : xnor_gate port map(I_0 => A(3), I_1 => B(3), O => s(3));


  AND0 : and_gate port map(I_0 => s(3), I_1 => s(2), O => o_and0);
  AND1 : and_gate port map(I_0 => o_and0, I_1 => s(1), O => o_and1);
  AND2 : and_gate port map(I_0 => o_and1, I_1 => s(0), O => m_A_eq_B);


  -- A>B --

  INV0 : inverter port map(I => B(0), O => not_B(0));
  INV1 : inverter port map(I => B(1), O => not_B(1));
  INV2 : inverter port map(I => B(2), O => not_B(2));
  INV3 : inverter port map(I => B(3), O => not_B(3));

  AND3  : and_gate port map(I_0 => A(3), I_1 => not_B(3), O => gate_O_and(0));
  AND4  : and_gate port map(I_0 => s(3), I_1 => A(2), O => gate_O_and(1));
  AND5  : and_gate port map(I_0 => gate_O_and(1), I_1 => not_B(2), O => gate_O_and(2));
  AND6  : and_gate port map(I_0 => gate_O_and(8), I_1 => A(1), O => gate_O_and(3));
  AND7  : and_gate port map(I_0 => gate_O_and(3), I_1 => not_B(1), O => gate_O_and(4));
  AND8  : and_gate port map(I_0 => gate_O_and(1), I_1 => s(1), O => gate_O_and(5));
  AND9  : and_gate port map(I_0 => gate_O_and(5), I_1 => A(0), O => gate_O_and(6));
  AND10 : and_gate port map(I_0 => gate_O_and(6), I_1 => not_B(0), O => gate_O_and(7));
  AND22 : and_gate port map(I_0 => s(3), I_1 => s(2), O => gate_O_and(8));

  OR0 : or_gate port map(I_0 => gate_O_and(0), I_1 => gate_O_and(2), O => gate_O_or(0));
  OR1 : or_gate port map(I_0 => gate_O_and(4), I_1 => gate_O_and(7), O => gate_O_or(1));
  OR2 : or_gate port map(I_0 => gate_O_or(0), I_1 => gate_O_or(1), O => m_A_gt_B);
  -- A<B --


  INV4 : inverter port map(I => A(0), O => not_A(0));
  INV5 : inverter port map(I => A(1), O => not_A(1));
  INV6 : inverter port map(I => A(2), O => not_A(2));
  INV7 : inverter port map(I => A(3), O => not_A(3));

  AND11 : and_gate port map(I_0 => B(3), I_1 => not_A(3), O => gate_O_and1(0));
  AND12 : and_gate port map(I_0 => s(3), I_1 => B(2), O => gate_O_and1(1));
  AND13 : and_gate port map(I_0 => gate_O_and1(1), I_1 => not_A(2), O => gate_O_and1(2));

  AND14 : and_gate port map(I_0 => gate_O_and1(8), I_1 => B(1), O => gate_O_and1(3));
  AND15 : and_gate port map(I_0 => gate_O_and1(3), I_1 => not_A(1), O => gate_O_and1(4));

  AND16 : and_gate port map(I_0 => gate_O_and1(8), I_1 => s(1), O => gate_O_and1(5));
  AND17 : and_gate port map(I_0 => gate_O_and1(5), I_1 => B(0), O => gate_O_and1(6));
  AND18 : and_gate port map(I_0 => gate_O_and1(6), I_1 => not_A(0), O => gate_O_and1(7));

  AND23 : and_gate port map(I_0 => s(3), I_1 => s(2), O => gate_O_and1(8));

  OR3 : or_gate port map(I_0 => gate_O_and1(0), I_1 => gate_O_and1(2), O => gate_O_or1(0));
  OR4 : or_gate port map(I_0 => gate_O_and1(4), I_1 => gate_O_and1(7), O => gate_O_or1(1));
  OR5 : or_gate port map(I_0 => gate_O_or1(0), I_1 => gate_O_or1(1), O => m_A_lt_B);
  --a_gt_b <= (m_a_eq_b and i_a_gt_b) or m_A_gt_B;
  AND19 : and_gate port map(I_0 => m_a_eq_b, I_1 => i_a_gt_b, O => wire(0));
  OR6   : or_gate port map(I_0 => wire(0), I_1 => m_A_gt_B, O => a_gt_B);

  --a_lt_b <= (m_a_eq_b and i_a_lt_b) or m_A_lt_B;
  AND20 : and_gate port map(I_0 => m_a_eq_b, I_1 => i_a_lt_b, O => wire(1));
  OR7   : or_gate port map(I_0 => wire(1), I_1 => m_A_lt_B, O => a_lt_B);

  --a_eq_b <= m_a_eq_b and i_A_eq_B;
  AND21 : and_gate port map(I_0 => m_a_eq_b, I_1 => i_a_eq_b, O => a_eq_b);

end architecture;