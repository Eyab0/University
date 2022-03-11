library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;
entity stage_1_comp is
  port (
    A      : in std_logic_vector (7 downto 0);
    B      : in std_logic_vector (7 downto 0);
    a_gt_b : out std_logic;
    a_lt_b : out std_logic;
    a_eq_b : out std_logic);
end entity;

architecture arch of stage_1_comp is

  component carry_look_ahead
    port (
      A     : in std_logic_vector (7 downto 0);
      B     : in std_logic_vector (7 downto 0);
      C_in  : in std_logic;
      S     : out std_logic_vector (7 downto 0);
      C_out : out std_logic
    );
  end component;

  signal not_b   : std_logic_vector(7 downto 0);
  signal not_a   : std_logic;
  signal sub_out : std_logic_vector(8 downto 0);
  signal P, G    : std_logic_vector(7 downto 0);
  signal wire    : std_logic_vector(37 downto 0);
begin

  GEN_NOT : for jj in 0 to 7 generate
    not_gen : inverter port map(B(jj), not_b(jj));
  end generate GEN_NOT;
  substractor : carry_look_ahead
  port map(
    A     => A,
    B     => not_b,
    C_in  => '1',
    S     => sub_out(7 downto 0),
    C_out => sub_out(8)
  );

  not_0 : inverter port map(A(7), not_a);
  and_0 : and_gate port map(not_a, B(7), wire(0));

  -- a_eq_b = nor(sub0,sub1,sub2,...sub7)
  or_gen0 : or_generic port map(sub_out(0), sub_out(1), sub_out(2), sub_out(3), sub_out(4), sub_out(5), sub_out(6), sub_out(7), wire(1));
  not_1   : inverter port map(wire(1), wire(2));

  a_eq_b <= wire(2);
  -- handling the overflow issues by the following line
  -- a_lt_b = (((a(7) and b(7)) or (not a(7) and not b(7))) and sub_out(7))  or (a(7) and b(7)')
  and_1 : and_gate port map(A(7), B(7), wire(3)); -- a(7) and b(7)
  and_2 : and_gate port map(not_a, not_b(7), wire(4)); --a(7)' and b(7)'
  or_0  : or_gate port map(wire(3), wire(4), wire(5)); --((a(7) and b(7)) or (not a(7) and not b(7)))
  and_3 : and_gate port map(wire(5), sub_out(7), wire(6)); --(a(7) and b(7)) or (not a(7) and not b(7))) and sub_out(7))
  and_4 : and_gate port map(a(7), not_b(7), wire(7)); --(a(7) and b(7)')
  or_1  : or_gate port map(wire(6), wire(7), wire(8)); -- a_lt_b

  a_lt_b <= wire(8);

  -- a_gt_b = a_lt_b nor a_eq_b
  nor_0 : nor_gate port map(wire(2), wire(8), a_gt_b);
end architecture;