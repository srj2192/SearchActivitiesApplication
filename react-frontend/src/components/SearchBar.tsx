import React from 'react';
import { Card, TextField } from '@mui/material';

type SearchBarProps = {
    onSearch: (value: string) => void;
};

const SearchBar: React.FC<SearchBarProps> = ({ onSearch }) => {
    return (
        <Card>
            <TextField
                label="Search Activities ..."
                variant="outlined"
                fullWidth
                onChange={(e) => onSearch(e.target.value)}
            />
        </Card>
    );
};

export default SearchBar;
