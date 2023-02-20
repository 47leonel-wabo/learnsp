import React from 'react'

class ListOfVideos extends React.Component {
    constructor(props){
        super(props)
        this.state = {data: []}
    }

    async componentDidMount(){
        let jsonData = await fetch('/api/videos').json()
        this.setState({data: jsonData})
    }

    render(){
        const {data} = this.state

        return (
            <ul>
                {data.map((item, index) => <li key={index}>{item.name}</li>)}
            </ul>
        )
    }
}

export default ListOfVideos