package org.ubp.ent.backend.model.classroom.equipements;

import org.junit.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by Anthony on 20/11/2015.
 */
public class QuantityTest {

    public static Quantity createValidQuantity() {
        return new Quantity(10);
    }

    @Test
    public void shouldNotInstantiateWithANullQuantity() {
        try {
            new Quantity(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isNotEmpty();
        }
    }

    @Test
    public void shouldNotInstantiateWithAZeroQuantity() {
        try {
            new Quantity(0);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isNotEmpty();
        }
    }

    @Test
    public void shouldNotInstantiateWithANegativeQuantity() {
        try {
            new Quantity(-2);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isNotEmpty();
        }
    }

}