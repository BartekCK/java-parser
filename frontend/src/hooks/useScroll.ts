import React from 'react';

function useScroll(handleScroll: (event: any) => void) {
    React.useLayoutEffect(() => {
        document.addEventListener('scroll', handleScroll);

        return () => {
            document.removeEventListener('scroll', handleScroll);
        };
    }, []);
}

export default useScroll;
