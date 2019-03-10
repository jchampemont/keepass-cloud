Vue.component('crud-table', {
    props: ["endpoint"],
    data: function() {
       return {
           rows: []
       }
    },
    methods: {
        add: function(row) {
            this.rows.push(row);
        },
        remove: function(id) {
            axios
                .delete('/api/' + this.endpoint + '/' + id)
                .then(response => {
                    this.rows = this.rows.filter(v => (v.id !== id));
                });
        },
        edit: function(id) {
            this.$refs.modal.edit(id);
        },
        refresh: function() {
            axios
                .get('/api/' + this.endpoint)
                .then(response => (this.rows = response.data))
        }
    },
    template: `
        <div>
            <b-table :data="rows">
                <template slot-scope="props">
                    <b-table-column field="id" label="ID" sortable>
                        {{ props.row.id }}
                    </b-table-column>
                    <b-table-column field="name" label="Nom" sortable>
                        {{ props.row.name }}
                    </b-table-column>
                    <b-table-column field="created" label="Date de creation" sortable>
                        <span class="tag is-success">
                            {{ new Date(props.row.created).toLocaleDateString() }} à {{ new Date(props.row.created).toLocaleTimeString() }}
                        </span>
                    </b-table-column>
                    <b-table-column field="modified" label="Date de dernière modification" sortable>
                        <span class="tag is-success" v-if="props.row.modified">
                            {{ new Date(props.row.modified).toLocaleDateString() }} à {{ new Date(props.row.modified).toLocaleTimeString() }}
                        </span>
                        <span v-if="!props.row.modified">
                            Jamais modifié.
                        </span>
                    </b-table-column>
                    <b-table-column label="Actions">
                        <button class="button is-small" type="button" @click="remove(props.row.id)">
                            <b-icon icon="delete" size="is-small"></b-icon>
                        </button>
                        <button class="button is-small" type="button" @click="edit(props.row.id)">
                            <b-icon icon="pencil" size="is-small"></b-icon>
                        </button>
                    </b-table-column>
                </template>
            </b-table>
            <hr />
            <crud-modal :endpoint="endpoint" ref="modal"></crud-modal>
        </div>
    `,
    mounted () {
        this.refresh();
    }
});