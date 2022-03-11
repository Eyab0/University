library ieee;
use ieee.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.std_logic_unsigned.all;

entity tb_comparator is
end tb_comparator;

architecture behavior of tb_comparator is

  -- Component Declaration for the Unit Under Test (UUT)

  --Inputs
  signal A : signed(7 downto 0) := (others => '0');
  signal B : signed(7 downto 0) := (others => '0');
  --Outputs
  signal a_gt_b : std_logic := '0';
  signal a_lt_b : std_logic := '0';
  signal a_eq_b : std_logic := '0';
  signal stable : std_logic := '0';

begin

  -- Instantiate the Unit Under Test (UUT)
  uut : entity work.stage_2_comp port map (
    A        => std_logic_vector(A),
    B        => std_logic_vector(B),
    a_gt_b_o => a_gt_b,
    a_lt_b_o => a_lt_b,
    a_eq_b_o => a_eq_b
    );
    
    
  stable <= a_gt_b xor a_eq_b xor a_lt_b;

  -- Stimulus process
  stim_proc : process
  begin
    -- hold reset state for 100 ns.
    wait for 1000 ns;
    for i in -128 to 127 loop
      A <= to_signed(i, 8);
      for j in -128 to 127 loop
        B <= to_signed(j, 8);
        wait for 200 ns;
        if A > B and stable = '1'  then
          assert (not(a_gt_b = '1'))
          report "a > b error"
            severity error;
        elsif B < A and stable = '1' then
          assert (not(a_lt_b = '1'))
          report "b > a error"
            severity error;
        elsif A = B and stable = '1' then
          assert (not(a_eq_b = '1'))
          report "a = b error"
            severity error;
        end if;
        
      end loop;

    end loop;

  end process;

end;