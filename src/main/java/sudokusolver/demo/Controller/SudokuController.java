package sudokusolver.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Service.SudokuService;

import java.util.Map;

@Controller
public class SudokuController {
    @Autowired
    private SudokuService sudokuService;

    @GetMapping("/")
    public String index() {
        return "views";
    }

    @PostMapping("/solve")
    public String solve(@RequestParam Map<String, String> params, Model model) {
        SudokuBoard board = new SudokuBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String value = params.get("cell_" + r + "_" + c);
                board.setCells(r, c, (value == null || value.isEmpty()) ? 0 : Integer.parseInt(value));
            }
        }

        board = sudokuService.solve(board);

        model.addAttribute("board", board.getCells());
        return "views";
    }
}
