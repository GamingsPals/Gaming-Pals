class Dao{

    constructor(array){
        this.elements = array;
    }

    remove(id){
        let element = this.find(id);
        let index = this.elements.indexOf(element);
        this.elements.splice(index,1);
    }

    save(entity){
        let element = this.find(entity.id);
        let index = this.elements.indexOf(element);
        this.elements[index] = entity;
    }

    find(id){
        return this.elements.find((a)=>{
            return a.id == id;
        })
    }

    get(){
        return this.elements;
    }
}