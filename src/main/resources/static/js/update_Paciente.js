// /js/update_Paciente.js
(() => {
    let editMode = false;
    let modalInstance = null;

    // Campos "bloqueados" permanentemente
    const LOCKED_IDS = new Set(['ver_paciente_id', 'ver_domi_id', 'ver_fechaIngreso']);

    // Selectors helpers
    const $ = (sel, root = document) => root.querySelector(sel);
    const $$ = (sel, root = document) => Array.from(root.querySelectorAll(sel));

    // Para ubicar y actualizar la fila en la tabla
    const TABLE_BODY_SEL = '#pacienteTableBody';

    function findRowByPacienteId(pacienteId) {
        // 1) Intento por id="tr_{id}"
        let row = document.getElementById(`tr_${pacienteId}`);
        if (row) return row;

        // 2) Intento por data-paciente-id
        row = $(`${TABLE_BODY_SEL} tr[data-paciente-id="${pacienteId}"]`);
        if (row) return row;

        // 3) Fallback: buscar por la primera celda (columna Id)
        const rows = $$( `${TABLE_BODY_SEL} tr` );
        for (const r of rows) {
            const firstCellText = r.cells?.[0]?.textContent?.trim();
            if (firstCellText == String(pacienteId)) return r;
        }
        return null;
    }

    function updatePacienteRow(p) {
        const row = document.getElementById(`tr_${p.id}`);
        if (!row) return;

        const setText = (sel, val) => {
            const td = row.querySelector(sel);
            if (td) td.textContent = val ?? '';
        };

        setText('.td_id', p.id);
        setText('.td_nombre', p.nombre);
        setText('.td_apellido', p.apellido);
        setText('.td_contacto', p.numeroContacto);
        setText('.td_fechaIngreso', p.fechaIngreso);
        setText('.td_email', p.email);
    }

    function setReadOnly(form, readonly) {
        $$('#ver_paciente_form input', document).forEach(inp => {
            if (LOCKED_IDS.has(inp.id)) {
                inp.readOnly = true;
                inp.classList.toggle('bg-light', true);
            } else {
                inp.readOnly = readonly;
                inp.classList.toggle('bg-light', readonly);
            }
        });
    }

    function fillForm(paciente) {
        $('#ver_paciente_id').value     = paciente.id ?? '';
        $('#ver_nombre').value          = paciente.nombre ?? '';
        $('#ver_apellido').value        = paciente.apellido ?? '';
        $('#ver_numContacto').value     = paciente.numeroContacto ?? '';
        $('#ver_fechaIngreso').value    = paciente.fechaIngreso ?? '';
        $('#ver_email').value           = paciente.email ?? '';
        $('#ver_domi_id').value         = paciente?.domicilio?.id ?? '';
        $('#ver_domi_calle').value      = paciente?.domicilio?.calle ?? '';
        $('#ver_domi_numero').value     = paciente?.domicilio?.numero ?? '';
        $('#ver_domi_localidad').value  = paciente?.domicilio?.localidad ?? '';
        $('#ver_domi_provincia').value  = paciente?.domicilio?.provincia ?? '';
    }

    function toPayload() {
        return {
            id: $('#ver_paciente_id').value,
            nombre: $('#ver_nombre').value,
            apellido: $('#ver_apellido').value,
            numeroContacto: $('#ver_numContacto').value,
            fechaIngreso: $('#ver_fechaIngreso').value, // readonly pero lo mandamos igual
            email: $('#ver_email').value,
            domicilio: {
                id: $('#ver_domi_id').value ? Number($('#ver_domi_id').value) : null,
                calle: $('#ver_domi_calle').value.trim(),
                numero: Number($('#ver_domi_numero').value),
                localidad: $('#ver_domi_localidad').value.trim(),
                provincia: $('#ver_domi_provincia').value.trim(),
            },
        };
    }

    function showModal() {
        const modalEl = document.getElementById('verPaciente');
        modalInstance = modalInstance || new bootstrap.Modal(modalEl);
        modalInstance.show();
    }

    function resetViewMode() {
        editMode = false;
        setReadOnly($('#ver_paciente_form'), true);
        const btn = $('#btnToggleEdit');
        btn.textContent = 'Modificar';
        btn.classList.remove('btn-success');
        btn.classList.add('btn-danger'); // rojo en modo "Modificar"
    }

    // Submit del formulario (Guardar Cambios)
    $('#ver_paciente_form')?.addEventListener('submit', (event) => {
        event.preventDefault();
        const pacienteId = $('#ver_paciente_id').value;
        const formData = toPayload();

        const url = `/paciente/${pacienteId}`;
        const settings = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        };

        fetch(url, settings)
            .then(res => {
                if (res.status === 204) return null;         // No Content
                if (!res.ok) return res.json().then(e => Promise.reject(e));
                return res.json();                            // Devuelve el paciente actualizado
            })
            .then((maybeUpdated) => {
                const updated = maybeUpdated ?? formData;     // Si no llega JSON, usamos lo que enviamos
                updatePacienteRow(updated);                   // 🔄 Actualización en vivo de la fila
                resetViewMode();                              // Volver a solo lectura

                showToast('Paciente actualizado', 'success');

            })
            .catch(err => {
                const msg = err?.message || err?.error || 'Error al actualizar el paciente';
                showToast(msg, 'danger');

            });
    });

    // Botón Modificar / Guardar Cambios
    $('#btnToggleEdit')?.addEventListener('click', () => {
        const btn = $('#btnToggleEdit');
        if (!editMode) {
            // 👉 Pasar a modo edición
            editMode = true;
            setReadOnly($('#ver_paciente_form'), false);
            btn.textContent = 'Guardar Cambios';
            btn.classList.remove('btn-danger');
            btn.classList.add('btn-success'); // 💚 verde en modo "Guardar Cambios"
        } else {
            // 💾 Guardar
            $('#ver_paciente_form').requestSubmit();
        }
    });

    // Exponer findBy en window (tu tabla lo llama así)
    window.findBy = function (id) {
        const url = '/paciente/BuscarPorId/' + id;
        fetch(url, { method: 'GET' })
            .then(r => r.json())
            .then(data => {
                fillForm(data);
                resetViewMode();
                showModal();
            })
            .catch(error => {
                const msg = "Error: " + (error?.message || error);
                showToast(msg, 'danger');
            });
    };

    function showToast(message, type = 'success') {
        let container = document.getElementById('toastContainer');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toastContainer';
            container.className = 'toast-container position-fixed top-0 end-0 p-3';
            document.body.appendChild(container);
        }
        const toastEl = document.createElement('div');
        toastEl.className = `toast align-items-center text-bg-${type} border-0`;
        toastEl.setAttribute('role', 'alert');
        toastEl.setAttribute('aria-live', 'assertive');
        toastEl.setAttribute('aria-atomic', 'true');
        toastEl.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">${message}</div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto"
                data-bs-dismiss="toast" aria-label="Close"></button>
      </div>`;
        container.appendChild(toastEl);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
        toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
    }

})();
