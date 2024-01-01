CREATE TABLE `data_siswa` (
  `id` int(11) NOT NULL,
  `nama` varchar(45) NOT NULL,
  `nisn` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `agama` varchar(45) NOT NULL,
  `asalsekolah` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `data_siswa`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `data_siswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;