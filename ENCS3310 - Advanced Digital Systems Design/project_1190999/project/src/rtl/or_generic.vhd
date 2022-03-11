library IEEE;
library work;
use work.gate_package.all;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity or_generic is
  generic (
    WIDTH : integer := 4
  );
  port (
    I_0 : in std_logic;
    I_1 : in std_logic;
    I_2 : in std_logic;
    I_3 : in std_logic;
    I_4 : in std_logic;
    I_5 : in std_logic;
    I_6 : in std_logic;
    I_7 : in std_logic;
    O   : out std_logic);

end entity;

architecture arch of or_generic is
  signal wire : std_logic_vector(5 downto 0);
begin
  GEN2 : if WIDTH = 2 generate
    or0: or_gate port map(I_0 => I_0, I_1 => I_1, O => O);
  end generate;

  GEN3 : if WIDTH = 3 generate
    or0: or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1: or_gate port map(I_0 => wire(0), I_1 => I_2, O => O);
  end generate;

  GEN4 : if WIDTH = 4 generate
    or0: or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1: or_gate port map(I_0 => wire(0), I_1 => I_2, O => wire(1));
    or2: or_gate port map(I_0 => wire(1), I_1 => I_3, O => O);
  end generate;

  GEN5 : if WIDTH = 5 generate
    or0: or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1: or_gate port map(I_0 => wire(0), I_1 => I_2, O => wire(1));
    or2: or_gate port map(I_0 => wire(1), I_1 => I_3, O => wire(2));
    or3: or_gate port map(I_0 => wire(2), I_1 => I_4, O => O);
  end generate;

  GEN6 : if WIDTH = 6 generate
    or0:or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1:or_gate port map(I_0 => wire(0), I_1 => I_2, O => wire(1));
    or2:or_gate port map(I_0 => wire(1), I_1 => I_3, O => wire(2));
    or3:or_gate port map(I_0 => wire(2), I_1 => I_4, O => wire(3));
    or4:or_gate port map(I_0 => wire(3), I_1 => I_5, O => O);
  end generate;

  GEN7 : if WIDTH = 7 generate
    or0: or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1: or_gate port map(I_0 => wire(0), I_1 => I_2, O => wire(1));
    or2: or_gate port map(I_0 => wire(1), I_1 => I_3, O => wire(2));
    or3: or_gate port map(I_0 => wire(2), I_1 => I_4, O => wire(3));
    or4: or_gate port map(I_0 => wire(3), I_1 => I_5, O => wire(4));
    or5: or_gate port map(I_0 => wire(4), I_1 => I_6, O => O);
  end generate;

  GEN8 : if WIDTH = 8 generate
    or0:or_gate port map(I_0 => I_0, I_1 => I_1, O => wire(0));
    or1:or_gate port map(I_0 => wire(0), I_1 => I_2, O => wire(1));
    or2:or_gate port map(I_0 => wire(1), I_1 => I_3, O => wire(2));
    or3:or_gate port map(I_0 => wire(2), I_1 => I_4, O => wire(3));
    or4:or_gate port map(I_0 => wire(3), I_1 => I_5, O => wire(4));
    or5:or_gate port map(I_0 => wire(4), I_1 => I_6, O => wire(5));
    or6:or_gate port map(I_0 => wire(5), I_1 => I_7, O => O);
  end generate;
end architecture;