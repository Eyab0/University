
library IEEE;
use IEEE.std_logic_1164.all;
package gate_package is
  component inverter is

    port (
      I : in std_logic;
      O : out std_logic);

  end component;

  component nand_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component nor_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component and_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component or_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component xnor_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component xor_gate is

    port (
      I_0 : in std_logic;
      I_1 : in std_logic;
      O   : out std_logic);

  end component;

  component AND_GENERIC is
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
  end component;

  component OR_GENERIC is
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
  end component;
end package;