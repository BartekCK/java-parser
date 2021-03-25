import React from 'react';

function useCLickOutside(base: React.RefObject<HTMLElement>, callback: () => void) {
    React.useEffect(() => {
        const handleClickOutside = (event) => {
            if (base.current && !base.current.contains(event.target)) {
                callback();
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [base]);
}

export default useCLickOutside;
