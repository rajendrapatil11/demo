package com.verinite.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.verinite.controller.ItemController;
import com.verinite.model.Item;
import com.verinite.repository.ItemRepository;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ItemTests {
  private static final int CHECKED_ITEM_ID = 1;
  private static final Item CHECKED_ITEM = new ItemBuilder()
    .id(CHECKED_ITEM_ID)
    .checked()
    .build();
  private static final Item UNCHECKED_ITEM = new ItemBuilder()
    .id(2)
    .checked()
    .build();
  private static final Item NEW_ITEM = new ItemBuilder()
    .checked()
    .build();
  @InjectMocks
  private ItemController controller;
  @Mock
  private ItemRepository repository;
  private ArgumentCaptor<Item> anyItem = ArgumentCaptor.forClass(Item.class);

  @Test
  public void whenFindingItemsItShouldReturnAllItems() {

    given(repository.findAll()).willReturn(Arrays.asList(CHECKED_ITEM, UNCHECKED_ITEM));

    assertThat(controller.findItems())
 
    .containsOnly(CHECKED_ITEM, UNCHECKED_ITEM);
  }
  @Test
  public void whenAddingItemItShouldReturnTheSavedItem() {

    given(repository.saveAndFlush(NEW_ITEM)).willReturn(CHECKED_ITEM);

    assertThat(controller.addItem(NEW_ITEM))
   
    .isSameAs(CHECKED_ITEM);
  }
  
 
  @Test
  public void whenUpdatingItemItShouldReturnTheSavedItem() {

    given(repository.getOne(CHECKED_ITEM_ID)).willReturn(CHECKED_ITEM);

    given(repository.saveAndFlush(CHECKED_ITEM)).willReturn(CHECKED_ITEM);
   
    assertThat(controller.updateItem(CHECKED_ITEM, CHECKED_ITEM_ID))

    .isSameAs(CHECKED_ITEM);
  }
  
  @Test
  public void whenUpdatingItemItShouldUseTheGivenID() {
   
    given(repository.getOne(CHECKED_ITEM_ID)).willReturn(CHECKED_ITEM);

    controller.updateItem(NEW_ITEM, CHECKED_ITEM_ID);

    verify(repository).saveAndFlush(anyItem.capture());
   
    assertThat(anyItem.getValue().getId()).isEqualTo(CHECKED_ITEM_ID);
  }
  
  @Test
  public void whenDeletingAnItemItShouldUseTheRepository() {
 
    controller.deleteItem(CHECKED_ITEM_ID);
   
    verify(repository).deleteById(2034);
  }
}
