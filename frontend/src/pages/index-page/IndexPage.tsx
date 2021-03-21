import React from 'react';

// hooks
import { useParams } from 'react-router-dom';

// components
import Header from '../../components/header';

// types
import { IRoute } from '../../core/types';

const IndexPage: React.FC = () => {
    const params: IRoute = useParams();

    React.useEffect(() => {
        console.log(params);
    }, [params]);

    return (
        <div>
            <Header />
        </div>
    );
};

export default IndexPage;
