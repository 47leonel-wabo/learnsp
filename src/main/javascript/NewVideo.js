import React from 'react'

class NewVideo extends React.Component {
    constructor(props){
        super(props)
        this.state = {name: ''}
    }

    const handleChane = event => {this.setState({name: event.target.value})}

    const handleSubmit = async event => {
        event.preventDefault()
        await fetch('/api/videos',
            {
                method: 'POST',
                headers: {
                    "Content-type": "application/json"
                },
                body: JSON.stringify({name: this.state.name})
            }).then(response => window.location.href = '/react')
    }

    render(){
        const {name} = this.state

        return (
            <form onSubmit={this.handleSubmit}>
                <input type="text" value={name} onChange={this.handleChange} />
                <button type="submit">Submit</Submit>
            </form>
        )
    }
}

export default NewVideo