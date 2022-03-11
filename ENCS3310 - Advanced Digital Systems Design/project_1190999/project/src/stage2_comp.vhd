library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;
entity stage_2_comp is
  port (
    A        : in std_logic_vector (7 downto 0);
    B        : in std_logic_vector (7 downto 0);
    A_gt_B_o : out std_logic;
    A_lt_B_o : out std_logic;
    A_eq_B_o : out std_logic);
end entity;

architecture arch of stage_2_comp is

  component magnitude_comparator_4bit
    port (
      A        : in std_logic_vector(3 downto 0);
      B        : in std_logic_vector(3 downto 0);
      i_a_gt_b : in std_logic;
      i_a_lt_b : in std_logic;
      i_a_eq_b : in std_logic;
      a_gt_B   : out std_logic;
      a_eq_B   : out std_logic;
      a_lt_B   : out std_logic
    );
  end component;

  component twos_complement
    port (
    A : in std_logic_vector (7 downto 0);
    neg_A : out std_logic_vector(7 downto 0)
  );
end component;


  signal m_A_gt_B : std_logic;
  signal m_A_lt_B : std_logic;
  signal m_A_eq_B : std_logic;
  
  signal A_gt_B : std_logic;
  signal A_lt_B : std_logic;
  signal A_eq_B : std_logic;
  
  signal A_high   : std_logic_vector(3 downto 0);
  signal B_high   : std_logic_vector(3 downto 0);

  signal A_gt_B_o_wire : std_logic;
  signal A_eq_B_o_wire : std_logic;
  signal not_A_eq_B : std_logic;
  signal not_A_gt_B : std_logic;
  signal not_a         : std_logic;
  signal not_b         : std_logic;
  signal wire          : std_logic_vector(5 downto 0);


  signal neg_A, neg_B : std_logic_vector(7 downto 0);

  signal comparator_in_A : std_logic_vector(7 downto 0);
  signal comparator_in_B : std_logic_vector(7 downto 0);

begin

  

  magnitude_comparator_4bit_low : magnitude_comparator_4bit
  port map(
    A        => comparator_in_A(3 downto 0),
    B        => comparator_in_B(3 downto 0),
    i_a_gt_b => '0',
    i_a_lt_b => '0',
    i_a_eq_b => '1',
    a_gt_B   => m_A_gt_B,
    a_eq_B   => m_A_eq_B,
    a_lt_B   => m_A_lt_B
  );

  A_high <= '0'  & comparator_in_A(6 downto 4);
  B_high <= '0'  & comparator_in_B(6 downto 4);
  magnitude_comparator_4bit_high : magnitude_comparator_4bit
  port map(
    A        => A_high,
    B        => B_high,
    i_a_gt_b => m_A_gt_B,
    i_a_lt_b => m_A_lt_B,
    i_a_eq_b => m_A_eq_B,
    a_gt_B   => A_gt_B,
    a_eq_B   => A_eq_B,
    a_lt_B   => A_lt_B
  );

  twos_complement_inst0 : twos_complement
  port map (
    A => A,
    neg_A => neg_A
  );

  twos_complement_inst1 : twos_complement
  port map (
    A => B,
    neg_A => neg_B
  );
  
  comparator_in_A <= ((A(7) & A(7) & A(7) & A(7) & A(7) & A(7) & A(7) & A(7)) and neg_A) or ((not_A & not_A & not_A & not_A & not_A & not_A & not_A & not_A) and A);
  comparator_in_B <= ((B(7) & B(7) & B(7) & B(7) & B(7) & B(7) & B(7) & B(7)) and neg_B) or ((not_B & not_B & not_B & not_B & not_B & not_B & not_B & not_B) and B);

  -- A > B

  INV0     : inverter port map(I => A(7), O => not_A);
  INV1     : inverter port map(I => A_eq_B, O => not_A_eq_B);
  INV2     : inverter port map(I => A_gt_B, O => not_A_gt_B);
  AND0     : and_gate port map(I_0 => not_A, I_1 => B(7), O => wire(0));
  AND1     : and_gate port map(I_0 => not_A, I_1 => A_gt_B, O => wire(1));
  AND_GEN0 : and_generic generic map(3) port map(B(7), not_A_gt_B, not_A_eq_B, '0', '0', '0', '0', '0', wire(2));
  OR_GEN0  : or_generic generic map(3) port map(wire(0), wire(1), wire(2), '0', '0', '0', '0', '0', A_gt_B_o_wire);

  A_gt_B_o <= A_gt_B_o_wire;

  -- A = B
  INV3 : inverter port map(I => B(7), O => not_B);
  AND2 : and_gate port map(I_0 => not_A, I_1 => not_B, O => wire(3));
  AND3 : and_gate port map(I_0 => A(7), I_1 => B(7), O => wire(4));
  OR0  : or_gate port map(I_0 => wire(3), I_1 => wire(4), O => wire(5));
  AND4 : and_gate port map(I_0 => wire(5), I_1 => A_eq_B, O => A_eq_B_o_wire);

  A_eq_B_o <= A_eq_B_o_wire;

  -- A < B

  NOR0 : nor_gate port map(I_0 => A_gt_B_o_wire, I_1 => A_eq_B_o_wire, O => A_lt_B_o);

end architecture;