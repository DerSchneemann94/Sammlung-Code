import {useState,useEffect}from 'react';

export function useIncreaseOnScrollEnd(initialValue=0,stepSize=20,pixelTriggerDistance=100){
    const [value,setValue]=useState(initialValue);
    useEffect(() => {
        function scroll() {
          if (window.innerHeight + window.scrollY >= document.body.scrollHeight - pixelTriggerDistance) {
            setValue(value + stepSize);
          }
        }
        window.addEventListener('scroll', scroll);
        return () => {
          window.removeEventListener('scroll', scroll);
        };
      }, [value]);
    return [value];
}