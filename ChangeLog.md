# Next

## Fixed
- Paginator crash on rapid clicks

## Added
- `ConfirmationDialog.cancel()`

## Changed
- ValuedField to `ValuedField<T>`
- `RangeBasedField<T>: SingleValuedField<T>` to `RangeField<T>: ValuedField<T>`
- SelectMany to use `kollections.List` instead of `kotlin.collections.List`
- `ConfirmationDialog.ui` to `ConfirmationDialog.state` 

## Removed
- `SingleValuedField` in favour of `ValuedField<T>`
- `ConfirmationState` in favour of `LazyState<Unit>`
- `kotlinx.collections.interoperable` infavour of `kollections`

------------------------------------
# Next

- [ ] Localized presenters-geo & presenters-krono test into their respective modules
- [x] Add a list of countries with the easy information you can find
- [x] Add location input

### 2022-08-30

- [x] Migrate dropdown into a ValuedField
- [x] Create a form (with custom params) with every possible field and test it out
- [x] Let form submit values with context. Currently, every thing is being treated as a string
    - [x] Submit multi select as a pure list
    - [x] Offer params to form which expect multiple selected items

### 2022-08-29

- [x] MultiSelectInput field should have methods for toggling selection of a specific item

### 2022-08-28

- [x] Created a multi select input field
    - [x] Wrote the damn class
    - [x] Wrote its Field builders
    - [x] Tested for multi selection
- [x] Created a single select input field
    - [x] Wrote the damn class
    - [x] Wrote its Field builders