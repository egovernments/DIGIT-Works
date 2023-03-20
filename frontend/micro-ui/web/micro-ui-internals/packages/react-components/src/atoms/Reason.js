import React,{Fragment} from "react";
// TODO @Nipun localise 
const Reason = ({ headComment, otherComment, additionalComment }) => {
    
    const getMarkup = () => {
        if(additionalComment) {
            return <div className="checkpoint-comments-wrap" style={{marginBottom:"1rem"}}>
                <p>I certify that appropriate amount of work has been completed. Muster roll has been verified against Measurement Book.</p>
                <br />
                <p>
                    <b> Note</b>: Once approved Payment Advice will be generated and send to JIT-FS for payment processing.
                </p>
                </div>
            
        }
        else {
            return <div className="checkpoint-comments-wrap">
                <h4>{headComment}</h4>
                <p>{otherComment}</p>
            </div>
        }
    }
    return (
        getMarkup()
    );
}

export default Reason;
