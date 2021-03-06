package net.sf.anathema.character.generic.impl.traits.limitation;

import net.sf.anathema.character.generic.character.ILimitationContext;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgeBasedLimitationTest {

  private ILimitationContext context = mock(ILimitationContext.class);
  private int absoluteLimit = 10;
  private AgeBasedLimitation limitation = new AgeBasedLimitation(absoluteLimit);

  @Test
  public void limitsTo5ForAgeBelow100() throws Exception {
    assertThatMaximumForAgeIs(99, 5);
  }

  @Test
  public void limitsTo6ForAgeBelow250() throws Exception {
    assertThatMaximumForAgeIs(249, 6);
  }

  @Test
  public void limitsTo7ForAgeBelow500() throws Exception {
    assertThatMaximumForAgeIs(499, 7);
  }

  @Test
  public void limitsTo8ForAgeBelow1000() throws Exception {
    assertThatMaximumForAgeIs(999, 8);
  }

  @Test
  public void doesNotLimitFrom1000YearsOnward() throws Exception {
    assertThatMaximumForAgeIs(1000, absoluteLimit);
  }

  private void assertThatMaximumForAgeIs(int age, int value) {
    when(context.getAge()).thenReturn(age);
    assertThat(limitation.getCurrentMaximum(context, false), is(value));
  }
}
