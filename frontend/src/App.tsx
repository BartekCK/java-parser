import React from 'react';
import { BrowserRouter} from 'react-router-dom';

// components
import IndexPage from './pages/index-page';
import Navigation from "./containers/navigation";

const App: React.FC = () => {
    return (
        <BrowserRouter>
            <Navigation />
            <IndexPage />
        </BrowserRouter>
    );
};

export default App;
