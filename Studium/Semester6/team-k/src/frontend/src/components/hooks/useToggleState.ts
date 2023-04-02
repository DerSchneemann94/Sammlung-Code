import { useState } from 'react';

export default function useToggleState(initialValue = false): [boolean, () => void] {
  const [value, setValue] = useState(initialValue);

  function toggleValue() {
    setValue(!value);
  }

  return [value, toggleValue];
}
